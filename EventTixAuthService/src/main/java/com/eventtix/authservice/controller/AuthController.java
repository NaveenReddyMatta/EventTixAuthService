package com.eventtix.authservice.controller;

import com.eventtix.authservice.Utility.JwtUtil;
import com.eventtix.authservice.feignclient.EventClient;
import com.eventtix.authservice.model.Event;
import com.eventtix.authservice.model.LoginResponse;
import com.eventtix.authservice.feignclient.UserClient;
import com.eventtix.authservice.model.LoginRequest;
import com.eventtix.authservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserClient userClient; // Feign Client to call User Service

    @Autowired
    EventClient eventClient;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
//        User user = userClient.getUserByEmail(email);
//
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            // Generate JWT Token
//            String token = jwtUtil.generateToken(email);
//            return ResponseEntity.ok(token);
//        }
//        return ResponseEntity.status(401).body("Invalid email or password");
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
             // 1. Validate user from User Service
            User user = userClient.validateUser(request);  // Throws error if invalid

            //  2. Generate token with role
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

            String role = user.getRole().name();

            List<Event> events = null;

            if ("USER".equalsIgnoreCase(role)) {
                return ResponseEntity.ok().body(new LoginResponse(token, user.getEmail(), user.getRole()));
            }
            else if ("ORGANIZER".equalsIgnoreCase(role)) {
                // Optionally fetch organizer-specific events
             //   events = eventClient.getEvents(); // or organizer-specific method
                return ResponseEntity.ok().body(new LoginResponse(token, user.getEmail(), user.getRole()));
            } else if ("ADMIN".equalsIgnoreCase(role)) {
                // Fetch all events and optionally users (via Feign if set up)
                return ResponseEntity.ok().body(new LoginResponse(token, user.getEmail(), user.getRole()));
            }




//            //  3. Call Event Service
//            List<Event> events = eventClient.getEvents();

            //  4. Return login response with token and events
           // LoginResponse response = new LoginResponse(token, user.getEmail(), user.getRole());
           return ResponseEntity.status(403).body("Inavlid Role");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(" Invalid email or password");
        }
    }
}
