package com.inventory.lbm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/ws/**", "/health").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(Customizer.withDefaults()) // browser login via Entra ID
            .logout(logout -> logout.logoutSuccessUrl("/").permitAll()) // optional: logout redirect
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // enable Bearer token auth

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/ws/**")); // allow websocket POSTs

        return http.build();
    }
}
