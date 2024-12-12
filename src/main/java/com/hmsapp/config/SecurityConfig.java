package com.hmsapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
    public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
        public SecurityFilterChain securityFilterChain(

                HttpSecurity http
        )throws Exception
        {
            //h(cd)2
            http.csrf().disable().cors().disable();
            http.addFilterBefore(jwtFilter, AuthorizationFilter.class);

            //haap
            //http.authorizeHttpRequests().anyRequest().permitAll();
            http.authorizeHttpRequests()
                    .requestMatchers("/api/auth/sign-up","/api/auth/login","/api/auth/property/sign-up")
                    .permitAll()
                    .requestMatchers("/api/v1/property/addProperty")
                    .hasRole("OWNER")
                    .requestMatchers("/api/v1/property/deleteProperty")
                    .hasAnyRole("OWNER","ADMIN")
                    .requestMatchers("/api/auth/blog/sign-up")
                    .hasRole("ADMIN")
                    .anyRequest().authenticated();


            return http.build();
        }

    }
