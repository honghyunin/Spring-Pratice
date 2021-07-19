package com.User.Management.advice.exception;


public class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }

    UserNotFoundException() {
    super();
    }
}
