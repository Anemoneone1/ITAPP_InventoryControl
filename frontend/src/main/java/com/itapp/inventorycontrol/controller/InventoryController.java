package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.front.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class InventoryController {
    private final RestTemplate restTemplate;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest httpSession){
        String token = (String) httpSession.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        ResponseEntity<DashboardDTO> responseEntity = restTemplate.exchange("http://andrii.demydeni.keenetic.name:8081/v1/dashboard", HttpMethod.GET, requestEntity, DashboardDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/auth/login";
        }

        model.addAttribute("dashboardDTO", responseEntity.getBody());
        model.addAttribute("createEmployeeDTO", new CreateEmployeeDTO());
        model.addAttribute("createWarehouseDTO", new CreateWarehouseDTO());
        return "dashboard";
    }



    @PostMapping("/create_warehouse")
    public String createWarehouse(@Validated @ModelAttribute("createWarehouse") CreateWarehouseDTO createWarehouseDTO, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<CreateWarehouseDTO> requestEntity = new HttpEntity<>(createWarehouseDTO, headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange("http://andrii.demydeni.keenetic.name:8081/v1/warehouse", HttpMethod.POST, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            httpServletRequest.getSession().setAttribute("token", responseEntity.getBody().getToken());
            return "redirect:/dashboard";
        }
        System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        return "redirect:/dashboard";
    }

    @PostMapping("/delete_warehouse/{id}")
    public String deleteWarehouse(@PathVariable("id") Long warehouseId, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<DeleteWarehouseDTO> requestEntity = new HttpEntity<>(new DeleteWarehouseDTO(warehouseId), headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange("http://andrii.demydeni.keenetic.name:8081/v1/warehouse", HttpMethod.DELETE, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/dashboard";
        }
        return "redirect:/dashboard";
    }
}
