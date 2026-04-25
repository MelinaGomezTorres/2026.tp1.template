package com.bibliotech.repository;

import com.bibliotech.model.Historial;

import java.util.*;

public class InMemoryHistorialRepository implements Repository<Historial, Integer> {

    private final Map<Integer, Historial> storage = new HashMap<>();

    @Override
    public void guardar(Historial historial) {
        storage.put(historial.hashCode(), historial);
    }

    @Override
    public Optional<Historial> buscarPorId(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Historial> buscarTodos() {
        return new ArrayList<>(storage.values());
    }
}