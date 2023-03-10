package com.frank.gateway.microgateway.controller;

import com.frank.gateway.microgateway.model.Role;
import com.frank.gateway.microgateway.security.UserPrincipal;
import com.frank.gateway.microgateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/change/{role}")
    public ResponseEntity<?> changeRole(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Role role) {
        userService.updateRole(role, userPrincipal.getUsername());
        return ResponseEntity.ok(true);
    }
}
