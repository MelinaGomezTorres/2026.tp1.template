package com.bibliotech.model;

import java.time.LocalDate;

public class Prestamo {

    private int socioId;
    private String isbn;
    private LocalDate fecha;

    public Prestamo(int socioId, String isbn) {
        this.socioId = socioId;
        this.isbn = isbn;
        this.fecha = LocalDate.now();
    }

    public int getSocioId() {
        return socioId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}