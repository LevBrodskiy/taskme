package com.blm.taskme.security.exception;

import org.springframework.security.core.AuthenticationException;

public class DefaultAuthenticationException extends AuthenticationException {
    public DefaultAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public DefaultAuthenticationException(String msg) {
        super(msg);
    }
}
