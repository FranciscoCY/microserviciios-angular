package com.frank.gateway.microgateway.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.frank.gateway.microgateway.security.UserPrincipal;
import com.frank.gateway.microgateway.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Integer JWT_EXPIRATION_IN_MS;

    @Autowired
    private SecurityUtil securityUtil;

    public String generateToken(UserPrincipal authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return JWT.create()
                .withSubject(authentication.getUsername())
                .withIssuer("auth0")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .withClaim("roles", authorities)
                .withClaim("userId", authentication.getId())
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
            DecodedJWT decodedJWT = verifier.verify(token);

            String username = decodedJWT.getSubject();
            Long userId = decodedJWT.getClaim("userId").asLong();
            Date expiration = decodedJWT.getExpiresAt();

            if(username == null || username.isEmpty()) return null;
            if (expiration.before(new Date())) return null;

            Set<GrantedAuthority> authoritySet = Arrays.stream(decodedJWT.getClaim("roles").asString().split(","))
                    .map(securityUtil::convertToAuthority)
                    .collect(Collectors.toSet());

            UserDetails userDetails = UserPrincipal.builder()
                    .username(username)
                    .authorities(authoritySet)
                    .id(userId)
                    .build();

            return new UsernamePasswordAuthenticationToken(userDetails, null, authoritySet);
        } catch (Exception ex) {
            throw new AccountExpiredException("Sesión expirada o token no válido");
        }
    }
}
