package com.bibliotech;

import com.bibliotech.model.*;
import com.bibliotech.repository.*;
import com.bibliotech.service.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== SISTEMA BIBLIOTECH ===");

        // Repositorios
        Repository<Libro, String> libroRepo = new InMemoryLibroRepository();
        Repository<Socio, Integer> socioRepo = new InMemorySocioRepository();
        Repository<Prestamo, Integer> prestamoRepo = new InMemoryPrestamoRepository();
        Repository<Historial, Integer> historialRepo = new InMemoryHistorialRepository();

        // Servicios
        PrestamoService prestamoService = new PrestamoService(
                libroRepo,
                socioRepo,
                prestamoRepo,
                historialRepo
        );

        LibroService libroService = new LibroServiceImpl(libroRepo);
        SocioService socioService = new SocioService(socioRepo);

        // Libros
        libroRepo.guardar(new Libro("1", "Clean Code", "Robert C. Martin", 2008, "Programación", TipoEjemplar.LIBRO_FISICO));
        libroRepo.guardar(new Libro("2", "Effective Java", "Joshua Bloch", 2018, "Programación", TipoEjemplar.EBOOK));
        libroRepo.guardar(new Libro("3", "Design Patterns", "GoF", 1994, "Arquitectura", TipoEjemplar.LIBRO_FISICO));
        libroRepo.guardar(new Libro("4", "Refactoring", "Martin Fowler", 1999, "Programación", TipoEjemplar.LIBRO_FISICO));
        libroRepo.guardar(new Libro("5", "Domain Driven Design", "Eric Evans", 2003, "Arquitectura", TipoEjemplar.LIBRO_FISICO));
        libroRepo.guardar(new Libro("6", "Clean Architecture", "Robert C. Martin", 2017, "Arquitectura", TipoEjemplar.EBOOK));

        // Socios
        socioRepo.guardar(new Estudiante(1, "Ana", "46161017", "ana@gmail.com"));
        socioRepo.guardar(new Estudiante(2, "Juan", "47111222", "juan@gmail.com"));
        socioRepo.guardar(new Estudiante(3, "Lucía", "44999111", "lucia@gmail.com"));

        socioRepo.guardar(new Docente(4, "Luis", "22058117", "luis@gmail.com"));
        socioRepo.guardar(new Docente(5, "María", "20000111", "maria@gmail.com"));
        socioRepo.guardar(new Docente(6, "Carlos", "25000222", "carlos@gmail.com"));

        socioService.registrar(new Estudiante(7, "Pedro", "47345678", "pedro@mail.com"));

        // PRÉSTAMOS
        System.out.println("\n===== REGISTRANDO PRÉSTAMOS =====");

        prestamoService.registrarPrestamo("1", 1);
        System.out.println("✔ Préstamo: Libro 1 → Socio 1");

        prestamoService.registrarPrestamo("2", 1);
        System.out.println("✔ Préstamo: Libro 2 → Socio 1");

        prestamoService.registrarPrestamo("3", 4);
        System.out.println("✔ Préstamo: Libro 3 → Socio 4");

        prestamoService.registrarPrestamo("4", 4);
        System.out.println("✔ Préstamo: Libro 4 → Socio 4");

        // DEVOLUCIONES
        System.out.println("\n===== REGISTRANDO DEVOLUCIONES =====");

        prestamoService.registrarDevolucion("1", 1);
        System.out.println("✔ Devolución: Libro 1 → Socio 1");

        prestamoService.registrarDevolucion("2", 1);
        System.out.println("✔ Devolución: Libro 2 → Socio 1");

        // PRUEBAS DE ERRORES
        System.out.println("\n===== PRUEBAS DE ERRORES =====");

        try {
            prestamoService.registrarPrestamo("999", 1);
        } catch (RuntimeException e) {
            System.out.println("❌ Error esperado: " + e.getMessage());
        }

        try {
            prestamoService.registrarPrestamo("1", 2);
            prestamoService.registrarPrestamo("2", 2);
            prestamoService.registrarPrestamo("5", 2);
            prestamoService.registrarPrestamo("6", 2);
        } catch (RuntimeException e) {
            System.out.println("❌ Error esperado (límite): " + e.getMessage());
        }

        // BÚSQUEDA
        System.out.println("\n===== BÚSQUEDA DE LIBROS =====");
        libroService.buscar("Java").forEach(l ->
                System.out.println("🔎 Encontrado: " + l.titulo())
        );

        // HISTORIAL
        System.out.println("\n===== HISTORIAL =====");
        prestamoService.obtenerHistorial().forEach(h ->
                System.out.println(h.getFecha()
                        + " | " + h.getOperacion()
                        + " | Libro: " + h.getIsbn()
                        + " | Socio: " + h.getSocioId())
        );

        // RETRASOS
        System.out.println("\n===== RETRASOS =====");
        prestamoRepo.buscarTodos().forEach(p -> {
            if (p.isDevuelto()) {
                long retraso = prestamoService.calcularDiasRetraso(p, 7);
                System.out.println("Libro " + p.getIsbn() + " - retraso: " + retraso + " días");
            }
        });

        // LIBROS
        System.out.println("\n===== LIBROS =====");
        libroRepo.buscarTodos().forEach(l ->
                System.out.println(l.titulo() + " (" + l.isbn() + ")")
        );

        // SOCIOS
        System.out.println("\n===== SOCIOS =====");
        socioRepo.buscarTodos().forEach(s ->
                System.out.println("DNI: " + s.getDni() + " - Email: " + s.getEmail())
        );

        // PRÉSTAMOS ACTIVOS
        System.out.println("\n===== PRÉSTAMOS ACTIVOS =====");

        prestamoRepo.buscarTodos().stream()
                .filter(p -> !p.isDevuelto())
                .forEach(p -> {

                    String tituloLibro = libroRepo.buscarPorId(p.getIsbn())
                            .map(Libro::titulo)
                            .orElse("Libro desconocido");

                    String infoSocio = socioRepo.buscarPorId(p.getSocioId())
                            .map(s -> s.getId() + " - " + s.getEmail())
                            .orElse("Socio desconocido");

                    System.out.println("Libro: " + tituloLibro +
                            " - Socio: " + infoSocio);
                });

        System.out.println("\n=== FIN DE EJECUCIÓN ===");
    }
}