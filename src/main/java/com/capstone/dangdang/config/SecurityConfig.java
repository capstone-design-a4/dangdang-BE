package com.capstone.dangdang.config;

import com.capstone.dangdang.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 사이트 위변조 요청 방지
        http
                .csrf((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/dangdang", "/dangdang/join", "/dangdang/login").permitAll()
                        .requestMatchers("/dangdang/info/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers("/dangdang/admin/**").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated()
                );

        http
                .formLogin((auth) -> auth.loginPage("/dangdang/login")
                        .loginProcessingUrl("/dangdang/login")
                        .defaultSuccessUrl("/dangdang")
                        .failureUrl("/dangdang/login")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                );

        http
                .logout((auth) -> auth
                        .logoutUrl("/dangdang/logout")

                );

        return http.build();
    }

}