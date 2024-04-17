package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.response.TokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/user")
public class AuthenticationController {

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUserAndCompany() {
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login() {
        return null;
    }
}
