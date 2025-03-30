package com.example.springboot_car_dealership.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVehicleStateException extends RuntimeException{

    public InvalidVehicleStateException(String message) {
        super(message);
    }
}
