package com.bibliotech.service;

import com.bibliotech.model.Libro;
import com.bibliotech.repository.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class LibroServiceImpl implements LibroService {

    private final Repository<Libro, String> libroRepo;

    public LibroServiceImpl(Repository<Libro, String> libroRepo) {
        this.libroRepo = libroRepo;
    }

    @Override
    public List<Libro> buscar(String texto) {
        return libroRepo.buscarTodos().stream()
                .filter(l ->
                        l.titulo().toLowerCase().contains(texto.toLowerCase()) ||
                                l.autor().toLowerCase().contains(texto.toLowerCase()) ||
                                l.categoria().toLowerCase().contains(texto.toLowerCase())
                )
                .collect(Collectors.toList());
    }
}