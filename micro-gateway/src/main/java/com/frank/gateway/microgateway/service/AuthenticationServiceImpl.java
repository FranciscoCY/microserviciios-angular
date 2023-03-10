package com.frank.gateway.microgateway.service;

import com.frank.gateway.microgateway.model.User;
import com.frank.gateway.microgateway.security.CustomReactiveManager;
import com.frank.gateway.microgateway.security.UserPrincipal;
import com.frank.gateway.microgateway.security.jwt.JwtProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private CustomReactiveManager authenticationManagerReactive;

    @Autowired
    private JwtProviderImpl jwtProvider;

    @Override
    public Mono<User> signInAndReturnJWT(User signInRequest){
        return authenticationManagerReactive.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        ).map(user -> (UserPrincipal) user.getPrincipal()).map(userPrincipal -> {
            User sigInUser = userPrincipal.getUser();
            sigInUser.setToken(jwtProvider.generateToken(userPrincipal));
            return  sigInUser;
        });
    }
}
