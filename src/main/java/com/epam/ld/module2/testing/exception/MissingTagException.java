package com.epam.ld.module2.testing.exception;

public class MissingTagException extends RuntimeException {

    public MissingTagException(String message) {
        super(message);
    }
}
