package com.credit.userms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.credit.userms.filter.JwtAuthenticationFilter;


/**
 * Security configuration class that enables web security and configures HTTP security rules.
 */
@EnableWebSecurity  // Enable Spring Security's web security support and provide Spring MVC and springboot integration.
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disable CSRF (Cross Site Request Forgery) protection since tokens are immune to this attack.
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // No session will be created or used by Spring Security.
            .and()
            .authorizeRequests()  // Allow configuring authorization requests.
                .requestMatchers("/login", "/register").permitAll()  // Allow everyone to access login and register endpoints.
                .anyRequest().authenticated()  // All other requests must be authenticated.
            .and()
            .oauth2Login()  // Enable OAuth2 login functionality.
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);  // Add custom JWT authentication filter before Spring's default username/password authentication filter.

        return http.build();  // Build and return the configured HttpSecurity instance.
    }
}
