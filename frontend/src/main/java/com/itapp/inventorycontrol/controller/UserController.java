package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.creation.CreateEmployeeDTO;
import com.itapp.inventorycontrol.dto.front.EmployeeDTO;
import com.itapp.inventorycontrol.dto.front.TokenDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RestTemplate restTemplate;
    private final String api;

    public UserController(RestTemplate restTemplate, @Value("${internal_api}") String api) {
        this.restTemplate = restTemplate;
        this.api = api;
    }

    @PostMapping("/create_employee")
    public String createEmployee(@Validated @ModelAttribute("createEmployee") CreateEmployeeDTO createEmployeeDTO, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<CreateEmployeeDTO> requestEntity = new HttpEntity<>(createEmployeeDTO, headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(api + "/user/employee", HttpMethod.POST, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "redirect:/user/employee_list";
        }
        System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        return "redirect:/user/employee_list";
    }

    @GetMapping("/employee_list")
    public String dashboard(Model model, HttpServletRequest httpSession) {
        String token = (String) httpSession.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<EmployeeDTO>> responseEntity = restTemplate.exchange(api + "/user", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<EmployeeDTO>>() {});

        model.addAttribute("createEmployeeDTO", new CreateEmployeeDTO());
        model.addAttribute("employeeListDTO", responseEntity.getBody());

        return "employee_list";
    }
}
