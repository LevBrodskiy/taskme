package com.blm.taskme.api.v1.advice;

import com.blm.taskme.api.v1.response.ErrorResponse;
import com.blm.taskme.service.exception.RegistrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<?> registrationExceptionHandle(RegistrationException e) {
        logger.info("Exception={}\nMessage={}", e, e.getMessage());
        ErrorResponse response = new ErrorResponse();
        response.addMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationExceptionHandle(ConstraintViolationException e) {
        logger.info("Exception={}\nMessage={}\nViolations={}",
                e, e.getMessage(), e.getConstraintViolations());
        ErrorResponse response = new ErrorResponse();

        List<String> messages = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        response.setMessages(messages);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
