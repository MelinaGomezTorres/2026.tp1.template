package com.bibliotech.service;

import com.bibliotech.model.Socio;
import com.bibliotech.repository.Repository;
import com.bibliotech.exception.*;

import java.util.regex.Pattern;

public class SocioService {

    private final Repository<Socio, Integer> repo;

    public SocioService(Repository<Socio, Integer> repo) {
        this.repo = repo;
    }

    public void registrar(Socio socio) {

        // validar email
        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", socio.email())) {
            throw new EmailInvalidoException("Email inválido");
        }

        // validar DNI único
        boolean existe = repo.buscarTodos().stream()
                .anyMatch(s -> s.dni().equals(socio.dni()));

        if (existe) {
            throw new DniDuplicadoException("DNI ya registrado");
        }

        repo.guardar(socio);
    }
}