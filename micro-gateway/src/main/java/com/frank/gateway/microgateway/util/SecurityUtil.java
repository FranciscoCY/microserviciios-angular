package com.frank.gateway.microgateway.util;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private static final String ROLE_PREFIX = "ROLE_";
    public SimpleGrantedAuthority convertToAuthority(String role) {
        String format = role.startsWith(ROLE_PREFIX) ? role : ROLE_PREFIX.concat(role);
        return new SimpleGrantedAuthority(format);
    }
}
