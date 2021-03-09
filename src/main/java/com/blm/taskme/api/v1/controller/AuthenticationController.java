package com.blm.taskme.api.v1.controller;

import com.blm.taskme.api.v1.request.JwtAuthenticationRequest;
import com.blm.taskme.api.v1.response.AuthenticationToken;
import com.blm.taskme.service.implementation.DefaultJwtAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1")
@RestController
public class AuthenticationController {
    private final DefaultJwtAuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(DefaultJwtAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<?> auth(@RequestBody JwtAuthenticationRequest request) {
        AuthenticationToken token = authenticationService.getAuthenticationToken(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
