package com.blm.taskme.service.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception {
    private List<String> violations = new ArrayList<>();

    public ValidationException() {
    }

    public ValidationException(List<String> violations) {
        this.violations = violations;
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public void addViolation(String violation) {
        violations.add(violation);
    }
}
