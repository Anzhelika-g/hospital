package org.example.hospital.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.hospital.config.JWTUtilService;
import org.example.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    private final JWTUtilService jwtUtil;
    private final UserService userService;

    @Autowired
    public LogoutController(JWTUtilService jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'LAB_ASSISTANT', 'ADMIN')")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token != null) {
            String username = jwtUtil.extractUsername(token);

            UserDetails userDetails = userService.getUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                return ResponseEntity.ok("Logged out successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token provided");
        }
    }
}
