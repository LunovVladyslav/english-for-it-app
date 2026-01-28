package com.englishforit.backend.controller;

import com.englishforit.backend.model.User;
import com.englishforit.backend.repository.UserRepository;
import com.englishforit.backend.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;
    // Note: We need to define AuthenticationManager bean if using full Spring
    // Security Flow
    // For this MVP, we might simulate it or use basic UserDetailsManager check
    // manually if complexity is high
    // But let's assume we configure it properly.

    // Simplification for Resource Server mode without full Auth Server:
    // We check password manually against DB (Bcrypt) and issue token.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(TokenService tokenService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Create manual authentication object for TokenService
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
                java.util.Collections.emptyList());
        return tokenService.generateToken(auth);
    }

    @PostMapping("/register")
    public String register(@RequestBody LoginRequest loginRequest) {
        if (userRepository.findByEmail(loginRequest.email()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(loginRequest.email());
        user.setPassword(passwordEncoder.encode(loginRequest.password()));
        user.setName(loginRequest.email()); // default name
        userRepository.save(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
                java.util.Collections.emptyList());
        return tokenService.generateToken(auth);
    }

    public record LoginRequest(String email, String password) {
    }
}
