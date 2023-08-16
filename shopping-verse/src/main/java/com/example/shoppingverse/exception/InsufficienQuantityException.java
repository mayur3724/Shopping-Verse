package com.example.shoppingverse.exception;

public class InsufficienQuantityException extends RuntimeException{
    public InsufficienQuantityException(String message){
        super(message);
    }
}
