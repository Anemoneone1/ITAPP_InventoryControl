package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.EmployeeCreateRequest;
import com.itapp.inventorycontrol.dto.request.EmployeeDeleteRequest;
import com.itapp.inventorycontrol.dto.response.UserResponse;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.entity.UserRole;
import com.itapp.inventorycontrol.mapper.UserMapper;
import com.itapp.inventorycontrol.service.TokenService;
import com.itapp.inventorycontrol.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/user")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllEmployees() {
        return new ResponseEntity<>(userService.getAll().stream()
                .map(userMapper::userToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping("/manager")
    public ResponseEntity<UserResponse> registerManager(@RequestBody EmployeeCreateRequest request) {
        User newUser = userMapper.requestToUser(request);
        newUser.setRole(UserRole.MANAGER);
        newUser = userService.createEmployee(newUser);

        return new ResponseEntity<>(
                userMapper.userToResponse(newUser),
                HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<UserResponse> registerEmployee(@RequestBody EmployeeCreateRequest request) {
        User newUser = userMapper.requestToUser(request);
        newUser.setRole(UserRole.EMPLOYEE);
        newUser = userService.createEmployee(newUser);

        return new ResponseEntity<>(
                userMapper.userToResponse(newUser),
                HttpStatus.OK);
    }

    @DeleteMapping("/employee")
    public ResponseEntity<UserResponse> removeEmployee(@RequestBody EmployeeDeleteRequest request) {
        userService.removeEmployee(request.getEmployeeId());
        tokenService.deleteAllTokensOfUser(request.getEmployeeId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO: add endpoint to change password
}
