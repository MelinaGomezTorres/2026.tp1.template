package com.bibliotech.repository;

import com.bibliotech.model.Libro;

import java.util.*;

public class InMemoryLibroRepository implements Repository<Libro, String> {

    private final Map<String, Libro> storage = new HashMap<>();

    @Override
    public void guardar(Libro libro) {
        storage.put(libro.isbn(), libro);
    }

    @Override
    public Optional<Libro> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Libro> buscarTodos() {
        return new ArrayList<>(storage.values());
    }
}