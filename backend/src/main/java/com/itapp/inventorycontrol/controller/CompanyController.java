package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.CompanyAndManagerCreateRequest;
import com.itapp.inventorycontrol.dto.response.TokenResponse;
import com.itapp.inventorycontrol.entity.Company;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.entity.UserRole;
import com.itapp.inventorycontrol.mapper.UserMapper;
import com.itapp.inventorycontrol.service.CompanyService;
import com.itapp.inventorycontrol.service.TokenService;
import com.itapp.inventorycontrol.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/company")
public class CompanyController {
    private final CompanyService companyService;
    private final UserService userService;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<TokenResponse> register(@RequestBody CompanyAndManagerCreateRequest request) {
        Company company = companyService.create(Company.builder()
                .name(request.getCompanyName())
                .build());
        User newUser = userMapper.requestToUser(request);
        newUser.setCompany(company);
        newUser.setRole(UserRole.MANAGER);
        userService.create(newUser);

        return new ResponseEntity<>(
                new TokenResponse(tokenService.generateToken(request.getEmail(), request.getPassword())),
                HttpStatus.OK);
    }
}
