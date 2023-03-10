package com.frank.gateway.microgateway.service;

import com.frank.gateway.microgateway.model.User;
import reactor.core.publisher.Mono;

public interface AuthenticationService  {
    Mono<User> signInAndReturnJWT(User signInRequest);
}
