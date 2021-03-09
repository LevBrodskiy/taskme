package com.blm.taskme.service.exception;

public class MemberAlreadyExistsException extends EntityAlreadyExistsException{
    public MemberAlreadyExistsException() {
    }

    public MemberAlreadyExistsException(String message) {
        super(message);
    }

    public MemberAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public MemberAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
