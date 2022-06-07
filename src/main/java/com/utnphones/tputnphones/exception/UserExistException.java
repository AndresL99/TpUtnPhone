package com.utnphones.tputnphones.exception;

public class UserExistException extends RuntimeException
{
    public UserExistException(String message) {
        super(message);
    }
}
