package com.bibliotech.exception;

public class PrestamoNoDevueltoException extends RuntimeException {
    public PrestamoNoDevueltoException(String msg) {
        super(msg);
    }
}