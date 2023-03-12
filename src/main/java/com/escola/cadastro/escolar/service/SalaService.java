package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.model.Sala;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.model.response.ResponseMaterias;
import com.escola.cadastro.escolar.model.response.ResponseSala;
import com.escola.cadastro.escolar.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
