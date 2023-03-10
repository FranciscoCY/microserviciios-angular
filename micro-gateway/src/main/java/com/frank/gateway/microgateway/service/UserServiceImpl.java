package com.frank.gateway.microgateway.service;

import com.frank.gateway.microgateway.model.Role;
import com.frank.gateway.microgateway.model.User;
import com.frank.gateway.microgateway.repository.UserRepository;
import com.frank.gateway.microgateway.security.UserPrincipal;
import com.frank.gateway.microgateway.security.jwt.JwtProviderImpl;
import com.frank.gateway.microgateway.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProviderImpl jwtProvider;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setFechaCreacion(LocalDateTime.now());

        User userCreated = userRepository.save(user);

        Set<GrantedAuthority> authorities = Set.of(securityUtil.convertToAuthority(user.getRole().name()));
        UserPrincipal userPrincipal = UserPrincipal
                .builder()
                .user(userCreated)
                .id(userCreated.getId())
                .username(userCreated.getUsername())
                .password(userCreated.getPassword())
                .authorities(authorities)
                .build();
        userCreated.setToken(jwtProvider.generateToken(userPrincipal));
        return userCreated;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void updateRole(Role role, String username) {
        userRepository.updateUserRole(username, role);
    }
}
