package com.bibliotech.model;

import java.time.LocalDate;

public class Prestamo {

    private final int socioId;
    private final String isbn;
    private final LocalDate fecha;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    public Prestamo(int socioId, String isbn) {
        this.socioId = socioId;
        this.isbn = isbn;
        this.fecha = LocalDate.now();
        this.devuelto = false;
        this.fechaDevolucion = null;
    }

    public void devolver() {
        this.devuelto = true;
        this.fechaDevolucion = LocalDate.now();
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

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
}