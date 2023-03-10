package com.frank.gateway.microgateway.security;

import com.frank.gateway.microgateway.model.User;
import com.frank.gateway.microgateway.service.UserService;
import com.frank.gateway.microgateway.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomReactiveUserDetail implements ReactiveUserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityUtil securityUtil;
    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Optional<User> user = userService.findByUsername(username);
        if(user.isEmpty()){
            return Mono.empty();
        }
        User data = user.get();
        Set<GrantedAuthority> authorities = Set.of(securityUtil.convertToAuthority(data.getRole().name()));

        return Mono.just(UserPrincipal.builder()
                .user(data)
                .id(data.getId())
                .username(data.getUsername())
                .password(data.getPassword())
                .authorities(authorities)
                .build());
    }
}