package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.model.Turma;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.model.response.ResponseFiltroTurma;
import com.escola.cadastro.escolar.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurmaService {
    @Autowired
    TurmaRepository turmaRepository;


    public ResponseEntity<DefaultResponse> listarTurmas() {
        List<Turma> turmas = new ArrayList<>();

        try {
            turmas = turmaRepository.findAll();
        }catch (Exception e){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .messagem(e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .data(new ResponseFiltroTurma(turmas))
                .status(HttpStatus.OK)
                .build());
    }

    public ResponseEntity buscaPorNumero(int numero) {
        return turmaRepository.findByNumero(numero)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity buscaPorAno(int ano) {
        return ResponseEntity.ok().body(turmaRepository.findByAno(ano));
    }
    public ResponseEntity buscaPorId(Long id) {
        return null;
    }
}

