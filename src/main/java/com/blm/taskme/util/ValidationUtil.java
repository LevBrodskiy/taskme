package com.blm.taskme.util;

import com.blm.taskme.api.v1.request.RegistrationRequest;
import com.blm.taskme.service.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtil {
    public static <T> void throwExceptionIfViolationsExists(Validator validator, T t) throws ValidationException {
        Set<ConstraintViolation<T>> violations =
                validator.validate(t);

        if (violations.size() > 0) {
            List<String> messages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            throw new ValidationException(messages);
        }
    }

    public static <T> void validate(Validator validator, T t) throws ValidationException {
        Set<ConstraintViolation<T>> violations =
                validator.validate(t);

        if (violations.size() > 0) {
            List<String> messages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            throw new ValidationException(messages);
        }
    }
}
