package com.User.Management.advice.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpstatus;

    public CustomException(String message, HttpStatus httpstatus){
        this.message=message;
        this.httpstatus=httpstatus;
    }
    public String getMessage(){
        return super.getMessage();
    }
    public HttpStatus getHttpstatus(){
        return httpstatus;
    }
}
