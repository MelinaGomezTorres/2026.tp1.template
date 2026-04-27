package com.bibliotech.exception;

public class LibroNoDisponibleException extends RuntimeException {
    public LibroNoDisponibleException(String msg) {
        super(msg);
    }
}