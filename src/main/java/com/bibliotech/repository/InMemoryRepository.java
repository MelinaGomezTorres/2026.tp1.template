package com.bibliotech.repository;

import java.util.*;

public class InMemoryRepository<T, ID> implements Repository<T, ID> {

    private final Map<ID, T> storage = new HashMap<>();

    @Override
    public void guardar(T entidad) {
        throw new UnsupportedOperationException(
                "Necesitas definir cómo obtener el ID para guardar"
        );
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> buscarTodos() {
        return new ArrayList<>(storage.values());
    }
}