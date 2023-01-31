package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.model.Sala;
import com.escola.cadastro.escolar.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaService {

    @Autowired
    SalaRepository salaRepository;
    public ResponseEntity listarSalas() {
        return ResponseEntity.ok().body(salaRepository.findAll());
    }

    public ResponseEntity buscaSalaPorId(Long id) {
        return salaRepository.findById(id)
                .map(record ->
                    ResponseEntity.ok().body(record)
                ).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
