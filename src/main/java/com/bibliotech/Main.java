package com.bibliotech;

import com.bibliotech.model.*;
import com.bibliotech.repository.*;
import com.bibliotech.service.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== SISTEMA BIBLIOTECH ===");

        // 📦 Repositorios
        Repository<Libro, String> libroRepo = new InMemoryLibroRepository();
        Repository<Socio, Integer> socioRepo = new InMemorySocioRepository();
        Repository<Prestamo, Integer> prestamoRepo = new InMemoryPrestamoRepository();
        Repository<Historial, Integer> historialRepo = new InMemoryHistorialRepository();

        // ⚙️ Servicio
        PrestamoService service = new PrestamoService(
                libroRepo,
                socioRepo,
                prestamoRepo,
                historialRepo
        );

        // 📚 Libros (agregamos más para pruebas)
        libroRepo.guardar(new Libro("1", "Clean Code", "Robert C. Martin", 2008, "Programación", TipoEjemplar.LIBRO_FISICO));
        libroRepo.guardar(new Libro("2", "Effective Java", "Joshua Bloch", 2018, "Programación", TipoEjemplar.EBOOK));
        libroRepo.guardar(new Libro("3", "Design Patterns", "GoF", 1994, "Arquitectura", TipoEjemplar.LIBRO_FISICO));
        libroRepo.guardar(new Libro("4", "Refactoring", "Martin Fowler", 1999, "Programación", TipoEjemplar.LIBRO_FISICO));
        libroRepo.guardar(new Libro("5", "Domain Driven Design", "Eric Evans", 2003, "Arquitectura", TipoEjemplar.LIBRO_FISICO));
        libroRepo.guardar(new Libro("6", "Clean Architecture", "Robert C. Martin", 2017, "Arquitectura", TipoEjemplar.EBOOK));

        // 👤 Socios
        socioRepo.guardar(new Estudiante(1, "Ana", "46161017", "ana@gmail.com"));
        socioRepo.guardar(new Estudiante(2, "Juan", "47111222", "juan@gmail.com"));
        socioRepo.guardar(new Estudiante(3, "Lucía", "44999111", "lucia@gmail.com"));

        socioRepo.guardar(new Docente(4, "Luis", "22058117", "luis@gmail.com"));
        socioRepo.guardar(new Docente(5, "María", "20000111", "maria@gmail.com"));
        socioRepo.guardar(new Docente(6, "Carlos", "25000222", "carlos@gmail.com"));

        // 🔄 PRÉSTAMOS
        System.out.println("\n===== REGISTRANDO PRÉSTAMOS =====");

        service.registrarPrestamo("1", 1);
        System.out.println("✔ Préstamo: Libro 1 → Socio 1");

        service.registrarPrestamo("2", 1);
        System.out.println("✔ Préstamo: Libro 2 → Socio 1");

        service.registrarPrestamo("3", 4);
        System.out.println("✔ Préstamo: Libro 3 → Socio 4");

        service.registrarPrestamo("4", 4);
        System.out.println("✔ Préstamo: Libro 4 → Socio 4");

        // 🔁 DEVOLUCIONES
        System.out.println("\n===== REGISTRANDO DEVOLUCIONES =====");

        service.registrarDevolucion("1", 1);
        System.out.println("✔ Devolución: Libro 1 → Socio 1");

        service.registrarDevolucion("2", 1);
        System.out.println("✔ Devolución: Libro 2 → Socio 1");

        // ❌ PRUEBAS DE ERRORES
        System.out.println("\n===== PRUEBAS DE ERRORES =====");

        try {
            service.registrarPrestamo("999", 1); // libro inexistente
        } catch (RuntimeException e) {
            System.out.println("❌ Error esperado: " + e.getMessage());
        }

        try {
            // ✔ ahora sí prueba real de límite (estudiante máx 3)
            service.registrarPrestamo("1", 2);
            service.registrarPrestamo("2", 2);
            service.registrarPrestamo("5", 2);
            service.registrarPrestamo("6", 2); // ESTE debe fallar
        } catch (RuntimeException e) {
            System.out.println("❌ Error esperado (límite): " + e.getMessage());
        }

        // 📜 HISTORIAL
        System.out.println("\n===== HISTORIAL =====");
        service.obtenerHistorial().forEach(h ->
                System.out.println(h.getOperacion()
                        + " - Libro: " + h.getIsbn()
                        + " - Socio: " + h.getSocioId())
        );

        // 📚 LIBROS
        System.out.println("\n===== LIBROS =====");
        libroRepo.buscarTodos().forEach(l ->
                System.out.println(l.titulo() + " (" + l.isbn() + ")")
        );

        // 👤 SOCIOS
        System.out.println("\n===== SOCIOS =====");
        socioRepo.buscarTodos().forEach(s ->
                System.out.println("DNI: " + s.getDni() + " - Email: " + s.getEmail())
        );

        // 📊 PRÉSTAMOS ACTIVOS
        System.out.println("\n===== PRÉSTAMOS ACTIVOS =====");
        prestamoRepo.buscarTodos().stream()
                .filter(p -> !p.isDevuelto())
                .forEach(p ->
                        System.out.println("Libro: " + p.getIsbn()
                                + " - Socio: " + p.getSocioId())
                );

        System.out.println("\n=== FIN DE EJECUCIÓN ===");
    }
}