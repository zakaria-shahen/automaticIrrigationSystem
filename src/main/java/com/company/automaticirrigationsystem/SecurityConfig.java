package com.company.automaticirrigationsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {


        httpSecurity
                .authorizeRequests()
                .mvcMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/irrigation_log").hasRole("IOT")
                .anyRequest().hasRole("ADMIN")
        ;

        httpSecurity.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return httpSecurity.build();
    }


}
