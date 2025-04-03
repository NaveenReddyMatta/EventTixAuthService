package com.eventtix.authservice.controller;

import com.eventtix.authservice.Utility.JwtUtil;
import com.eventtix.authservice.feignclient.UserClient;
import com.eventtix.authservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserClient userClient; // Feign Client to call User Service

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        User user = userClient.getUserByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Generate JWT Token
            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}
