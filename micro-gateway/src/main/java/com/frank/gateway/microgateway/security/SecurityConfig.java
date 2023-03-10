package com.frank.gateway.microgateway.security;

import com.frank.gateway.microgateway.model.Role;
import com.frank.gateway.microgateway.security.jwt.JwtAuthenticationFilter;
import com.frank.gateway.microgateway.security.jwt.JwtProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private CustomReactiveUserDetail customerUserDetailService;

    @Autowired
    private CustomReactiveManager manager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProviderImpl jwtProvider;

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .cors().disable()
                .authorizeExchange()
                .pathMatchers("/auth/authentication/sign-in","/auth/authentication/sign-up")
                .permitAll()
                .pathMatchers("/api/**")
                .hasRole(Role.ADMIN.name())
                .anyExchange()
                .authenticated()
                .and()
                .addFilterAt(new JwtAuthenticationFilter(manager, jwtProvider), SecurityWebFiltersOrder.AUTHORIZATION)
                .build();
    }
}
