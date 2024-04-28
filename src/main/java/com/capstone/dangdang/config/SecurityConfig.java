package com.capstone.dangdang.config;

import com.capstone.dangdang.config.auth.AuthFailureHandler;
import com.capstone.dangdang.config.auth.PrincipalDetailsService;
import com.capstone.dangdang.enums.Role;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthFailureHandler authFailureHandler;
    private final PrincipalDetailsService principalDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .csrf((csrfConfig) -> csrfConfig.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                        .requestMatchers( "/join", "/login").permitAll()
                        .requestMatchers("/api/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin((formLogin) ->
                        formLogin
                                .loginProcessingUrl("/login")
                                .usernameParameter("loginId")
                                .passwordParameter("password")
                                .successHandler((request, response, authentication) -> {
                                    response.setStatus(HttpServletResponse.SC_OK);
                                })
                                .failureHandler(authFailureHandler)
                )
                .userDetailsService(principalDetailsService);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}