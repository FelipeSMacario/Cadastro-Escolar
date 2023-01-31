package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.service.DiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "dia")
public class DiaController {

    @Autowired
    DiaService diaService;
    @GetMapping(value = "/listar")
    public ResponseEntity listarSalas() {
        return diaService.listarDias();
    }
}
