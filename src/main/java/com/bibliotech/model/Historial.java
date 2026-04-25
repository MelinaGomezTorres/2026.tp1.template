package com.bibliotech.model;

import java.time.LocalDate;

public class Historial {

    private int socioId;
    private String isbn;
    private String operacion; // PRESTAMO / DEVOLUCION
    private LocalDate fecha;

    public Historial(int socioId, String isbn, String operacion) {
        this.socioId = socioId;
        this.isbn = isbn;
        this.operacion = operacion;
        this.fecha = LocalDate.now();
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

    public LocalDate getFecha() {
        return fecha;
    }
}