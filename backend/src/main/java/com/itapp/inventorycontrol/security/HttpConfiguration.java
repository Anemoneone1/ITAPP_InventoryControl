package com.itapp.inventorycontrol.security;

import com.itapp.inventorycontrol.controller.APIVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class HttpConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // Swagger
                .requestMatchers(HttpMethod.GET, "/v3/api-docs.yaml").permitAll()
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()

                // AuthenticationController
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/user/register").permitAll()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/user/login").permitAll()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/user/logout").authenticated()

                // WarehouseController
                .requestMatchers(HttpMethod.GET, APIVersion.current + "/warehouse").authenticated()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/warehouse").authenticated()
                .requestMatchers(HttpMethod.PUT, APIVersion.current + "/warehouse").authenticated()
                .requestMatchers(HttpMethod.DELETE, APIVersion.current + "/warehouse").authenticated()

                .anyRequest().denyAll()
        );

        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());
        http.cors(Customizer.withDefaults());

        return http.build();
    }
}
