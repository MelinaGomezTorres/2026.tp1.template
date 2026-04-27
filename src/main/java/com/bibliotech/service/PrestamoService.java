package com.bibliotech.service;

import com.bibliotech.model.*;
import com.bibliotech.repository.Repository;
import com.bibliotech.exception.*;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class PrestamoService {

    private final Repository<Libro, String> libroRepo;
    private final Repository<Socio, Integer> socioRepo;
    private final Repository<Prestamo, Integer> prestamoRepo;
    private final Repository<Historial, Integer> historialRepo;

    public PrestamoService(
            Repository<Libro, String> libroRepo,
            Repository<Socio, Integer> socioRepo,
            Repository<Prestamo, Integer> prestamoRepo,
            Repository<Historial, Integer> historialRepo) {

        this.libroRepo = libroRepo;
        this.socioRepo = socioRepo;
        this.prestamoRepo = prestamoRepo;
        this.historialRepo = historialRepo;
    }

    public void registrarPrestamo(String isbn, int socioId) {

        Socio socio = socioRepo.buscarPorId(socioId)
                .orElseThrow(() -> new SocioNoExisteException("Socio no existe"));

        libroRepo.buscarPorId(isbn)
                .orElseThrow(() -> new LibroNoExisteException("Libro no existe"));

        boolean prestado = prestamoRepo.buscarTodos().stream()
                .anyMatch(p -> p.getIsbn().equals(isbn) && !p.isDevuelto());

        if (prestado) {
            throw new LibroNoDisponibleException("Libro no disponible");
        }

        long cantidad = prestamoRepo.buscarTodos().stream()
                .filter(p -> p.getSocioId() == socioId && !p.isDevuelto())
                .count();

        if (cantidad >= socio.maxLibros()) {
            throw new LimitePrestamoException("Límite de libros alcanzado");
        }

        Prestamo prestamo = new Prestamo(socioId, isbn);
        prestamoRepo.guardar(prestamo);

        historialRepo.guardar(new Historial(socioId, isbn, "PRESTAMO"));
    }

    public void registrarDevolucion(String isbn, int socioId) {

        Prestamo prestamo = prestamoRepo.buscarTodos().stream()
                .filter(p -> p.getIsbn().equals(isbn)
                        && p.getSocioId() == socioId
                        && !p.isDevuelto())
                .findFirst()
                .orElseThrow(() -> new PrestamoNoEncontradoException("Préstamo no encontrado"));

        prestamo.devolver();

        prestamoRepo.guardar(prestamo);

        historialRepo.guardar(new Historial(socioId, isbn, "DEVOLUCION"));
    }

    public long calcularDiasRetraso(Prestamo prestamo, int diasPermitidos) {

        if (!prestamo.isDevuelto()) {
            throw new PrestamoNoDevueltoException("El libro aún no fue devuelto");
        }

        long dias = ChronoUnit.DAYS.between(
                prestamo.getFecha(),
                prestamo.getFechaDevolucion()
        );

        return Math.max(0, dias - diasPermitidos);
    }

    public List<Historial> obtenerHistorial() {
        return historialRepo.buscarTodos();
    }
}