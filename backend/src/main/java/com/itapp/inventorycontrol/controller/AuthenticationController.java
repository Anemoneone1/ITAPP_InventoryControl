package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.LoginRequest;
import com.itapp.inventorycontrol.dto.request.UserCreateRequest;
import com.itapp.inventorycontrol.dto.response.TokenResponse;
import com.itapp.inventorycontrol.entity.Company;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.entity.UserRole;
import com.itapp.inventorycontrol.service.CompanyService;
import com.itapp.inventorycontrol.service.TokenService;
import com.itapp.inventorycontrol.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/user")
public class AuthenticationController {
    private final CompanyService companyService;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registerUserAndCompany(@RequestBody UserCreateRequest request) {
        Company company = companyService.create(Company.builder()
                .name(request.getCompanyName())
                .build());
        userService.create(User.builder()
                .company(company)
                .name(request.getName())
                .surname(request.getSurname())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.MANAGER)
                .build());

        return new ResponseEntity<>(
                new TokenResponse(
                        tokenService.generateToken(request.getEmail(), request.getPassword())
                ),
                HttpStatus.OK);
    }

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
