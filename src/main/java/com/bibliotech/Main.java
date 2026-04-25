package com.bibliotech;

import com.bibliotech.model.*;
import com.bibliotech.repository.*;
import com.bibliotech.service.*;

public class Main {

    public static void main(String[] args) {

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

        // 📚 Libros
        libroRepo.guardar(new Libro(
                "1",
                "Clean Code",
                "Robert C. Martin",
                2008,
                "Programación",
                TipoEjemplar.LIBRO_FISICO
        ));

        libroRepo.guardar(new Libro(
                "2",
                "Effective Java",
                "Joshua Bloch",
                2018,
                "Programación",
                TipoEjemplar.EBOOK
        ));

        libroRepo.guardar(new Libro(
                "3",
                "Design Patterns",
                "GoF",
                1994,
                "Arquitectura",
                TipoEjemplar.LIBRO_FISICO
        ));

        // 👤 Socios
        socioRepo.guardar(new Estudiante(1, "Ana", "123", "ana@mail.com"));
        socioRepo.guardar(new Docente(2, "Luis", "456", "luis@mail.com"));

        // 🔄 PRÉSTAMOS
        service.registrarPrestamo("1", 1);
        service.registrarPrestamo("2", 1);
        service.registrarPrestamo("3", 2);

        // ❌ intento de préstamo repetido (debería fallar si lo probás)
        // service.registrarPrestamo("1", 2);

        // 🔁 DEVOLUCIONES
        service.registrarDevolucion("1", 1);
        service.registrarDevolucion("2", 1);

        // 📜 HISTORIAL
        System.out.println("\n===== HISTORIAL =====");
        service.obtenerHistorial().forEach(h ->
                System.out.println(h.getOperacion() + " - Libro: " + h.getIsbn() + " - Socio: " + h.getSocioId())
        );

        // 📊 ESTADO FINAL
        System.out.println("\n===== LIBROS =====");
        libroRepo.buscarTodos().forEach(l ->
                System.out.println(l.titulo() + " (" + l.isbn() + ")")
        );

        System.out.println("\n===== SOCIOS =====");
        socioRepo.buscarTodos().forEach(s ->
                System.out.println(s.getDni() + " - " + s.getEmail())
        );

        System.out.println("\n===== PRÉSTAMOS ACTIVOS =====");
        prestamoRepo.buscarTodos().forEach(p ->
                System.out.println(p.getIsbn() + " - socio " + p.getSocioId())
        );
    }
}