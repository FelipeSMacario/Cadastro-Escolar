package com.example.Turma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Turma.controller.api.SalaApi;
import com.example.Turma.model.response.DefaultResponse;
import com.example.Turma.service.SalaService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "sala")
public class SalaController implements SalaApi {

    @Autowired
    SalaService salaService;
    @GetMapping(value = "/listar")
    public ResponseEntity<DefaultResponse> listarSalas() {
        return salaService.listarSalas();
    }

    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<DefaultResponse> buscarSalas(@PathVariable Long id) {
        return salaService.buscaSalaPorId(id);
    }
}
