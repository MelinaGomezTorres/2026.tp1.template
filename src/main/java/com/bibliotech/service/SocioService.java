package com.bibliotech.service;

import com.bibliotech.model.Socio;
import com.bibliotech.repository.Repository;
import com.bibliotech.exception.DniDuplicadoException;
import com.bibliotech.exception.EmailInvalidoException;

import java.util.regex.Pattern;

public class SocioService {

    private final Repository<Socio, Integer> repo;

    public SocioService(Repository<Socio, Integer> repo) {
        this.repo = repo;
    }

    public void registrar(Socio socio) {

        // validar email
        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", socio.getEmail())) {
            throw new EmailInvalidoException("Email inválido");
        }

        // validar DNI único
        boolean existe = repo.buscarTodos().stream()
                .anyMatch(s -> s.getDni().equals(socio.getDni()));

        if (existe) {
            throw new DniDuplicadoException("DNI ya registrado");
        }

        repo.guardar(socio);
    }
}