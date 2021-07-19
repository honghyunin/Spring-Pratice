package com.User.Management.advice.exception;

public class UserAlreadyExistsException extends RuntimeException{
    UserAlreadyExistsException(String msg, Throwable t){
        super(msg, t);
    }
    public UserAlreadyExistsException(){
        super();
    }
}
