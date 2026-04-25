package com.bibliotech.service;

import com.bibliotech.model.*;
import com.bibliotech.repository.Repository;

import java.time.temporal.ChronoUnit;

public class PrestamoService {

    private final Repository<Libro, String> libroRepo;
    private final Repository<Socio, Integer> socioRepo;
    private final Repository<Prestamo, Integer> prestamoRepo;

    public PrestamoService(
            Repository<Libro, String> libroRepo,
            Repository<Socio, Integer> socioRepo,
            Repository<Prestamo, Integer> prestamoRepo) {

        this.libroRepo = libroRepo;
        this.socioRepo = socioRepo;
        this.prestamoRepo = prestamoRepo;
    }

    public void registrarPrestamo(String isbn, int socioId) {

        Socio socio = socioRepo.buscarPorId(socioId)
                .orElseThrow(() -> new RuntimeException("Socio no existe"));

        libroRepo.buscarPorId(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no existe"));

        boolean prestado = prestamoRepo.buscarTodos().stream()
                .anyMatch(p -> p.getIsbn().equals(isbn) && !p.isDevuelto());

        if (prestado) {
            throw new RuntimeException("Libro no disponible");
        }

        long cantidad = prestamoRepo.buscarTodos().stream()
                .filter(p -> p.getSocioId() == socioId && !p.isDevuelto())
                .count();

        if (cantidad >= socio.maxLibros()) {
            throw new RuntimeException("Límite de libros alcanzado");
        }

        Prestamo prestamo = new Prestamo(socioId, isbn);
        prestamoRepo.guardar(prestamo);
    }

    public void registrarDevolucion(String isbn, int socioId) {

        Prestamo prestamo = prestamoRepo.buscarTodos().stream()
                .filter(p -> p.getIsbn().equals(isbn)
                        && p.getSocioId() == socioId
                        && !p.isDevuelto())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        prestamo.devolver();

        prestamoRepo.guardar(prestamo);
    }

    public long calcularDiasRetraso(Prestamo prestamo, int diasPermitidos) {

        if (!prestamo.isDevuelto()) {
            throw new RuntimeException("El libro aún no fue devuelto");
        }

        long dias = ChronoUnit.DAYS.between(
                prestamo.getFecha(),
                prestamo.getFechaDevolucion()
        );

        return Math.max(0, dias - diasPermitidos);
    }
}