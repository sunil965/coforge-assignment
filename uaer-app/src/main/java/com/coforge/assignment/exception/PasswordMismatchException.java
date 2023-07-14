package com.coforge.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(String resourceName) {
        super(String.format("Incorrect %s.", resourceName));
    }

}
