package com.atosnasmartphone.auth_service.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String username) {

        super("User with name " + username +" already exist");
    }
}
