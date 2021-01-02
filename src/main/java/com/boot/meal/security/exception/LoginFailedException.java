package com.boot.meal.security.exception;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(){
        super(ErrorCode.Login_FAILED.getMessage());
    }

    private LoginFailedException(String msg){
        super(msg);
    }
}
