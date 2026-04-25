package com.bibliotech.repository;

import java.util.*;

public class InMemoryRepository<T, ID> implements Repository<T, ID> {

    private final Map<ID, T> storage = new HashMap<>();

    @Override
    public void guardar(T entidad) {
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        return Optional.empty();
    }

    @Override
    public List<T> buscarTodos() {
        return new ArrayList<>();
    }
}