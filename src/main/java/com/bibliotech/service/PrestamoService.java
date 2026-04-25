package com.bibliotech.service;

import com.bibliotech.model.*;
import com.bibliotech.repository.Repository;
import com.bibliotech.exception.*;

import java.util.List;
import java.util.Optional;

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

        // 1. buscar socio
        Socio socio = socioRepo.buscarPorId(socioId)
                .orElseThrow(() -> new RuntimeException("Socio no existe"));

        // 2. buscar libro
        Libro libro = libroRepo.buscarPorId(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no existe"));

        // 3. validar disponibilidad (simplificado)
        boolean prestado = prestamoRepo.buscarTodos().stream()
                .anyMatch(p -> p.getIsbn().equals(isbn));

        if (prestado) {
            throw new RuntimeException("Libro no disponible");
        }

        // 4. validar límite de libros del socio
        long cantidad = prestamoRepo.buscarTodos().stream()
                .filter(p -> p.getSocioId() == socioId)
                .count();

        if (cantidad >= socio.maxLibros()) {
            throw new RuntimeException("Límite de libros alcanzado");
        }

        // 5. registrar préstamo
        Prestamo prestamo = new Prestamo(socioId, isbn);
        prestamoRepo.guardar(prestamo);
    }
}