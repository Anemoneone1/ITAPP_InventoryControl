package com.itapp.inventorycontrol.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class QrController {

    private final RestTemplate restTemplate;
    private final String api;

    public QrController(RestTemplate restTemplate, @Value("${internal_api}") String api) {
        this.restTemplate = restTemplate;
        this.api = api;
    }

    @GetMapping("/qrscanner")
    public String qrScanner(HttpServletRequest httpServletRequest) {
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "login";
        }
        return "qrscanner";
    }

    @PostMapping("/process-qr")
    @ResponseBody
    public Map<String, Long> processQR(@RequestBody Map<String, String> payload, HttpServletRequest httpServletRequest) {
        String qrData = payload.get("data");

        String token = (String) httpServletRequest.getSession().getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(qrData, headers);

        Map<String, Long> response = new HashMap<>();
        ResponseEntity<Long> responseEntity = restTemplate.exchange(api + "/qr", HttpMethod.GET, requestEntity, Long.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            response.put("action", responseEntity.getBody());
        }
        else {
            response.put("action", (long) -1);
        }

        return response;
    }

    @DeleteMapping("/process-qr")
    public String deleteByQR(@RequestBody Map<String, String> payload, HttpServletRequest httpServletRequest) {
        Long id = Long.valueOf(payload.get("data"));
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<Long> requestEntity = new HttpEntity<>(id, headers);

        restTemplate.delete(api + "/qr", HttpMethod.DELETE, requestEntity, Long.class);

        return "redirect:/qrscanner";
    }
}
