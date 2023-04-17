package com.login.loginexpiration.controller;

import com.login.loginexpiration.model.AuthenticationResponse;
import com.login.loginexpiration.model.LoginRequest;
import com.login.loginexpiration.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticateUser(loginRequest);
        return ResponseEntity.ok(authenticationResponse);
    }
}
