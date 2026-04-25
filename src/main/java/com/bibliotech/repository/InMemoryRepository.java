package com.bibliotech.repository;

import java.util.*;

public class InMemoryRepository<T, ID> implements Repository<T, ID> {

    private final Map<ID, T> storage = new HashMap<>();

    @Override
    public void guardar(T entidad) {
        // ⚠️ esto necesita que tengas una forma de obtener el ID
        // normalmente se maneja desde el service o se pasa como parámetro
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