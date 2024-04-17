package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.EmployeeCreateRequest;
import com.itapp.inventorycontrol.dto.request.EmployeeDeleteRequest;
import com.itapp.inventorycontrol.dto.request.LoginRequest;
import com.itapp.inventorycontrol.dto.request.UserCreateRequest;
import com.itapp.inventorycontrol.dto.response.TokenResponse;
import com.itapp.inventorycontrol.dto.response.UserResponse;
import com.itapp.inventorycontrol.entity.Company;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.mapper.UserMapper;
import com.itapp.inventorycontrol.service.CompanyService;
import com.itapp.inventorycontrol.service.TokenService;
import com.itapp.inventorycontrol.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/user")
public class UserController {
    private final CompanyService companyService;
    private final UserService userService;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @PostMapping("/manager")
    public ResponseEntity<TokenResponse> registerManager(@RequestBody UserCreateRequest request) {
        Company company = companyService.create(Company.builder()
                .name(request.getCompanyName())
                .build());
        User newUser = userMapper.requestToUser(request);
        newUser.setCompany(company);
        userService.create(newUser);

        return new ResponseEntity<>(
                new TokenResponse(
                        tokenService.generateToken(request.getEmail(), request.getPassword())
                ),
                HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<UserResponse> registerEmployee(@RequestBody EmployeeCreateRequest request) {
        User newUser = userService.createEmployee(userMapper.requestToUser(request));

        return new ResponseEntity<>(
                userMapper.userToResponse(newUser),
                HttpStatus.OK);
    }

    @DeleteMapping("/employee")
    public ResponseEntity<UserResponse> removeEmployee(@RequestBody EmployeeDeleteRequest request) {
        userService.removeEmployee(request.getEmployeeId());

        return new ResponseEntity<>(HttpStatus.OK);
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

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllEmployees() {
        return new ResponseEntity<>(userService.getAll().stream()
                .map(userMapper::userToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    //TODO: add endpoint to change roles of employees
}
