package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.creation.CreateAgreementDTO;
import com.itapp.inventorycontrol.dto.creation.CreateComplianceDTO;
import com.itapp.inventorycontrol.dto.front.ComplianceDTO;
import com.itapp.inventorycontrol.dto.page.CompliancePageDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/compliance")
public class ComplianceController {
    private final RestTemplate restTemplate;
    private final String api;

    public ComplianceController(RestTemplate restTemplate, @Value("${internal_api}") String api) {
        this.restTemplate = restTemplate;
        this.api = api;
    }

    @GetMapping()
    public String getCompliances(Model model, HttpServletRequest httpSession){
        String token = (String) httpSession.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<CompliancePageDTO>> responseEntity = restTemplate.exchange(api + "/compliance/history", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<CompliancePageDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/dashboard";
        }

        model.addAttribute("compliances", responseEntity.getBody());
        model.addAttribute("complianceCreationDTO", new CreateComplianceDTO());

        return "compliance_list";
    }

    @PostMapping()
    public String createCompliance(CreateComplianceDTO createComplianceDTO, HttpServletRequest httpSession){
        String token = (String) httpSession.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<CreateComplianceDTO> requestEntity = new HttpEntity<>(createComplianceDTO, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(api + "/compliance", HttpMethod.POST, requestEntity, Object.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        }

        return "redirect:/compliance";
    }

    @GetMapping("/agreement")
    public String agreementCreationPage(Model model, HttpServletRequest httpSession){
        String token = (String) httpSession.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<ComplianceDTO>> responseEntity = restTemplate.exchange(api + "/compliance", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ComplianceDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/compliance_list";
        }

        model.addAttribute("compliances", responseEntity.getBody());
        model.addAttribute("agreementCreationDTO", new CreateAgreementDTO());

        return "create_agreement";
    }

    @PostMapping("/agreement")
    public String createAgreement(CreateAgreementDTO createAgreementDTO, HttpServletRequest httpSession){
        String token = (String) httpSession.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<CreateAgreementDTO> requestEntity = new HttpEntity<>(createAgreementDTO, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(api + "/compliance-agreement", HttpMethod.POST, requestEntity, Object.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        }

        return "redirect:/compliance";
    }

}
