package com.frank.gateway.microgateway.security.jwt;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AuthenticationWebFilter {
    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtProviderImpl jwtProvider;
    public static final String AUTH_TOKEN_TYPE = "Bearer";
    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_TOKEN_PREFIX = AUTH_TOKEN_TYPE.concat(" ");

    public JwtAuthenticationFilter(ReactiveAuthenticationManager authenticationManager, JwtProviderImpl jwtProvider) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;

        setServerAuthenticationConverter(exchange -> {
            String token = extractTokenFromHeader(exchange.getRequest().getHeaders().getFirst(AUTH_HEADER));

            if(token == null || token.isEmpty()) return Mono.empty();
            return Mono.just(jwtProvider.validateToken(token));
        });
    }

    private String extractTokenFromHeader(String header) {
        if (StringUtils.hasText(header) && header.startsWith(AUTH_TOKEN_PREFIX)) {
            return header.substring(7);
        }
        return null;
    }
}
