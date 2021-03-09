package com.blm.taskme.api.v1.controller;

import com.blm.taskme.api.v1.request.RegistrationRequest;
import com.blm.taskme.service.exception.RegistrationException;
import com.blm.taskme.service.exception.ValidationException;
import com.blm.taskme.service.implementation.DefaultUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Registration")
@RestController
@RequestMapping(value = "/api/v1")
public class RegistrationController {
    private final DefaultUserService userService;
    private final static Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    @Autowired
    public RegistrationController(DefaultUserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "registration")
    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) throws RegistrationException, ValidationException {
        logger.info("Register: {}", request.getEmail());
        userService.register(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
