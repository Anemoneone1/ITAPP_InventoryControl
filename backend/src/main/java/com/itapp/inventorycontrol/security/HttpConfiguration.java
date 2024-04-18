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

                // UserController
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/user/manager").permitAll()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/user/login").permitAll()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/user/logout").authenticated()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/user/employee").authenticated()
                .requestMatchers(HttpMethod.DELETE, APIVersion.current + "/user/employee").authenticated()
                .requestMatchers(HttpMethod.GET, APIVersion.current + "/user").authenticated()

                // WarehouseController
                .requestMatchers(HttpMethod.GET, APIVersion.current + "/warehouse").authenticated()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/warehouse").authenticated()
                .requestMatchers(HttpMethod.PUT, APIVersion.current + "/warehouse").authenticated()
                .requestMatchers(HttpMethod.DELETE, APIVersion.current + "/warehouse").authenticated()

                // DashboardController
                .requestMatchers(HttpMethod.GET, APIVersion.current + "/dashboard").authenticated()

                // StorageConditionController
                .requestMatchers(HttpMethod.GET, APIVersion.current + "/storage-condition").authenticated()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/storage-condition").authenticated()
                .requestMatchers(HttpMethod.DELETE, APIVersion.current + "/storage-condition").authenticated()

                // ComplianceController
                .requestMatchers(HttpMethod.GET, APIVersion.current + "/compliance").authenticated()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/compliance").authenticated()
                .requestMatchers(HttpMethod.DELETE, APIVersion.current + "/compliance").authenticated()

                // ItemController
                .requestMatchers(HttpMethod.GET, APIVersion.current + "/item").authenticated()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/item").authenticated()
                .requestMatchers(HttpMethod.PUT, APIVersion.current + "/item").authenticated()
                .requestMatchers(HttpMethod.DELETE, APIVersion.current + "/item").authenticated()

                // StorageSpaceController
                .requestMatchers(HttpMethod.GET, APIVersion.current + "/storage").authenticated()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/storage").authenticated()
                .requestMatchers(HttpMethod.DELETE, APIVersion.current + "/storage").authenticated()

                // StorageSpaceItemController
                .requestMatchers(HttpMethod.GET, APIVersion.current + "/storage-items").authenticated()
                .requestMatchers(HttpMethod.POST, APIVersion.current + "/storage-items").authenticated()

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
