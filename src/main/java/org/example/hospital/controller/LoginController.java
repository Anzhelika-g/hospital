package org.example.hospital.controller;

import lombok.AllArgsConstructor;
import org.example.hospital.config.JWTUtilService;
import org.example.hospital.dto.UserDTO;
import org.example.hospital.service.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value ="/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final JWTUtilService jwtUtilService;

    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserDTO dto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(dto.getUsername());
        final String jwt = jwtUtilService.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }
}
