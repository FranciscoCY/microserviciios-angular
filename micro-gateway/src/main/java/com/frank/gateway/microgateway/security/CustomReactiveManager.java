package com.frank.gateway.microgateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomReactiveManager implements ReactiveAuthenticationManager {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomReactiveUserDetail userDetail;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (authentication.isAuthenticated()) return Mono.just(authentication);

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        return userDetail.findByUsername(username)
                .flatMap(userDetails -> {
                    if(passwordEncoder.matches(password, userDetails.getPassword())) {
                        return Mono.just(new UsernamePasswordAuthenticationToken(
                                userDetails, userDetails.getPassword(), userDetails.getAuthorities()
                        ));
                    }
                    return Mono.error(new BadCredentialsException("Invalid credentials"));
                });
    }
}