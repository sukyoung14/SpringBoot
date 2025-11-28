package com.example.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/","/info").permitAll()   // 두개의 라우팅은 열고
                    .anyRequest().authenticated()       // 니머지 모두는 로그인 필요
            )
            .formLogin(form -> form
                    .permitAll()
            )
            .logout(logout -> logout
                    .permitAll()
            );

        return http.build();
    }
}
