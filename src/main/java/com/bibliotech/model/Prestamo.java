package com.bibliotech.model;

import java.time.LocalDate;

public class Prestamo {

    private int socioId;
    private String isbn;
    private LocalDate fecha;
    private boolean devuelto;

    public Prestamo(int socioId, String isbn) {
        this.socioId = socioId;
        this.isbn = isbn;
        this.fecha = LocalDate.now();
        this.devuelto = false;
    }

    public void devolver() {
        this.devuelto = true;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public int getSocioId() {
        return socioId;
    }

    public String getIsbn() {
        return isbn;
    }
}