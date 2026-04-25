package com.bibliotech.model;

public record Libro(
        String isbn,
        String titulo,
        String autor,
        int anio,
        String categoria,
        TipoEjemplar tipo
) implements Recurso {}