package com.blm.taskme.api.v1.controller;

import com.blm.taskme.api.v1.response.UserProfileResponse;
import com.blm.taskme.service.exception.UserNotFoundException;
import com.blm.taskme.service.implementation.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final DefaultUserService userService;

    @Autowired
    public UserController(DefaultUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal String username) throws UserNotFoundException {
        UserProfileResponse response = userService.getProfile(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
