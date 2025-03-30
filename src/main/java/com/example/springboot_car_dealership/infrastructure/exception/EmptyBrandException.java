package com.example.springboot_car_dealership.infrastructure.exception;

public class EmptyBrandException extends RuntimeException{
    public EmptyBrandException(String message) {
        super(message);
    }
}
