package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.front.CreateEmployeeDTO;
import com.itapp.inventorycontrol.dto.front.DashboardDTO;
import com.itapp.inventorycontrol.dto.front.TokenDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final RestTemplate restTemplate;

    @PostMapping("/create_employee")
    public String createEmployee(@Validated @ModelAttribute("createEmployee") CreateEmployeeDTO createEmployeeDTO, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<CreateEmployeeDTO> requestEntity = new HttpEntity<>(createEmployeeDTO, headers);



        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange("http://andrii.demydeni.keenetic.name:8081/v1/user/employee", HttpMethod.POST, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "redirect:/dashboard";
        }
        System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        return "redirect:/dashboard";
    }
}
