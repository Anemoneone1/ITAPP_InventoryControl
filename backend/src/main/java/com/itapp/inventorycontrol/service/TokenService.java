package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TokenService {
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final UserService userService;

    public String generateToken(String email, String password) {
        User user = userService.getByEmailOrThrow(email);
        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        String token = jwtService.generateToken(email);
        jwtService.saveToken(user.getId(), token);
        return token;
    }

    public void deleteToken(String token) {
        jwtService.deleteToken(token);
    }
}
