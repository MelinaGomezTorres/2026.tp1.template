package com.bibliotech.exception;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException(String msg) {
        super(msg);
    }
}