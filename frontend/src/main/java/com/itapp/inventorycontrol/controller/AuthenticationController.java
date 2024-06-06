package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.front.RegisterDTO;
import com.itapp.inventorycontrol.dto.front.TokenDTO;
import com.itapp.inventorycontrol.dto.front.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private final RestTemplate restTemplate;
    private final String api;

    public AuthenticationController(RestTemplate restTemplate, @Value("${internal_api}") String api) {
        this.restTemplate = restTemplate;
        this.api = api;
    }
    @GetMapping("/login")
    public String login(Model model, HttpSession httpSession) {
        String token = (String) httpSession.getAttribute("token");
        if (token != null) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("userLoginDTO", new UserLoginDTO());
            return "login";
        }
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, @ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO) {

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Basic " + encodedCredentials);
        HttpEntity<UserLoginDTO> requestEntity = new HttpEntity<>(userLoginDTO);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(api + "/auth/login", HttpMethod.POST, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            request.getSession().setAttribute("token", responseEntity.getBody().getToken());
            return "redirect:/dashboard";
        } else {
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String register(Model model, HttpSession httpSession) {
        String token = (String) httpSession.getAttribute("token");
        if (token != null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/dashboard";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("registerDTO") RegisterDTO registerDTO, HttpServletRequest request) {
        HttpEntity<RegisterDTO> requestEntity = new HttpEntity<>(registerDTO);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(api + "/company", HttpMethod.POST, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            request.getSession().setAttribute("token", responseEntity.getBody().getToken());
            return "redirect:/dashboard";
        }
        System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        return "redirect:/auth/register";
    }
}
