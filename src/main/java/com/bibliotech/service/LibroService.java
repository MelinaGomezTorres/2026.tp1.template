package com.bibliotech.service;

import com.bibliotech.model.Libro;

import java.util.List;

public interface LibroService {
    List<Libro> buscar(String texto);
}