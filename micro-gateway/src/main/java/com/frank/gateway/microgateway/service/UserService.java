package com.frank.gateway.microgateway.service;

import com.frank.gateway.microgateway.model.Role;
import com.frank.gateway.microgateway.model.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void updateRole(Role role, String username);
}
