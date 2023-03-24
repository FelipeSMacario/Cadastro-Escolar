package com.example.Turma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Turma.model.Sala;
import com.example.Turma.model.response.DefaultResponse;
import com.example.Turma.model.response.ResponseSala;
import com.example.Turma.repository.SalaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaService {

    @Autowired
    SalaRepository salaRepository;
    public ResponseEntity<DefaultResponse> listarSalas() {

        List<Sala> salas = new ArrayList<>();

        try {
            salas = salaRepository.findAll();
        }catch (Exception e){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .messagem(e.getMessage())
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(false)
                .status(HttpStatus.OK)
                .data(new ResponseSala(salas))
                .build());
    }

    public ResponseEntity<DefaultResponse> buscaSalaPorId(Long id) {

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(false)
                .status(HttpStatus.OK)
                .data(salaRepository.findById(id).get())
                .build());
    }
}
