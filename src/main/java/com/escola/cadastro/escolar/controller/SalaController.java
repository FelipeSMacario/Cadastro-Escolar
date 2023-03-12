package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.SalaApi;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
