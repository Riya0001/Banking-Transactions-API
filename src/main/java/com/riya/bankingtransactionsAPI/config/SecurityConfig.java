package com.riya.bankingtransactionsAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // ✅ Allow all requests (skip authentication)
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // ✅ Disable Basic Authentication
                .formLogin(formLogin -> formLogin.disable()); // ✅ Disable login form authentication

        return http.build();
    }
}
