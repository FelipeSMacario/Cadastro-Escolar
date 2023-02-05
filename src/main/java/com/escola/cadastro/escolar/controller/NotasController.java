package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.NotasApi;
import com.escola.cadastro.escolar.dto.NotasDTO;
import com.escola.cadastro.escolar.dto.NotasPessoaDTO;
import com.escola.cadastro.escolar.dto.NotasTrimestreDTO;
import com.escola.cadastro.escolar.service.NotasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "notas")
@CrossOrigin(origins = "http://localhost:4200")
public class NotasController implements NotasApi {

    @Autowired
    NotasService notasService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarNotas(@RequestBody NotasDTO notasDTO){
        return notasService.cadastrarNotas(notasDTO);
    }

    @PutMapping(value = "/alterar")
    public ResponseEntity alterarNotas(@RequestBody NotasTrimestreDTO notasDTO){
        return notasService.alterarNotas(notasDTO);
    }

    @DeleteMapping(value = "/deletar")
    public ResponseEntity deletarNotas(@RequestBody NotasTrimestreDTO notasDTO){
        return notasService.deletarNotas(notasDTO);
    }

    @GetMapping(value = "/listar")
    public ResponseEntity listarNotas(@RequestBody NotasPessoaDTO notasPessoaDTO){
        return notasService.listarNotas(notasPessoaDTO);
    }
}
