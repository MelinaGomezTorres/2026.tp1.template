package com.bibliotech.service;

import com.bibliotech.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {
    List<Libro> buscar(String texto);
}