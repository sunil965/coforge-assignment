package com.coforge.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistingEmailException extends RuntimeException {

    public ExistingEmailException(String resourceName, String value) {
        super(String.format("%s with %s already exist", resourceName, value));
    }
}
