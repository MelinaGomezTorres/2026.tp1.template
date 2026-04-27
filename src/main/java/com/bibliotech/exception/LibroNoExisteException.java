package com.bibliotech.exception;

public class LibroNoExisteException extends RuntimeException {
    public LibroNoExisteException(String msg) {
        super(msg);
    }
}