package com.example.Turma.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Turma.model.Turma;
import com.example.Turma.model.response.DefaultResponse;
import com.example.Turma.model.response.ResponseFiltroTurma;
import com.example.Turma.repository.TurmaRepository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurmaService {
    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    ValidacoesService validacoesService;

    public ResponseEntity<DefaultResponse> listarTurmas() {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .data((Serializable) turmaRepository.findAll())
                .status(HttpStatus.OK)
                .build());
    }

    public ResponseEntity<DefaultResponse> buscaPorNumero(int numero) {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .data(validacoesService.buscaTurmaPorNumero(numero))
                .status(HttpStatus.OK)
                .build());
    }

    public ResponseEntity<DefaultResponse> buscaPorAno(int ano) {
        List<Turma> turmas = turmaRepository.findByAno(ano);

        if (turmas.isEmpty()){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .timestamp(LocalDate.now())
                    .messagem("Nenhuma turma identificada para esse ano")
                    .status(HttpStatus.NOT_FOUND)
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .data(new ResponseFiltroTurma(turmas))
                .status(HttpStatus.OK)
                .build());
    }
}

