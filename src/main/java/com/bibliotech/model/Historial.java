package com.bibliotech.model;

import java.time.LocalDateTime;

public class Historial {

    private final int socioId;
    private final String isbn;
    private final String operacion;
    private final LocalDateTime fecha;

    public Historial(int socioId, String isbn, String operacion) {
        this.socioId = socioId;
        this.isbn = isbn;
        this.operacion = operacion;
        this.fecha = LocalDateTime.now();
    }

    public int getSocioId() {
        return socioId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getOperacion() {
        return operacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}