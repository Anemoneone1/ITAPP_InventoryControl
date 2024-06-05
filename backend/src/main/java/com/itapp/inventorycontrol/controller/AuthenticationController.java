package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.LoginRequest;
import com.itapp.inventorycontrol.dto.response.TokenResponse;
import com.itapp.inventorycontrol.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/auth")
public class AuthenticationController {
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Validated LoginRequest request) {
        return new ResponseEntity<>(
                new TokenResponse(tokenService.generateToken(request.getEmail(), request.getPassword())),
                HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        String jwt = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        tokenService.deleteToken(jwt);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
