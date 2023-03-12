package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.NotasApi;
import com.escola.cadastro.escolar.dto.NotasDTO;
import com.escola.cadastro.escolar.dto.NotasPessoaDTO;
import com.escola.cadastro.escolar.dto.NotasTrimestreDTO;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
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
    public ResponseEntity<DefaultResponse> cadastrarNotas(@RequestBody NotasDTO notasDTO){
        return notasService.cadastrarNotas(notasDTO);
    }

    @PutMapping(value = "/alterar")
    public ResponseEntity<DefaultResponse> alterarNotas(@RequestBody NotasTrimestreDTO notasDTO){
        return notasService.alterarNotas(notasDTO);
    }


    @GetMapping(value = "/buscar/porMatricula/{matricula}")
    public ResponseEntity<DefaultResponse> buscarPorMatricula(@PathVariable Long matricula){
        return notasService.buscaPorMatricula(matricula);
    }

    @GetMapping(value = "buscar/{id}")
    public ResponseEntity<DefaultResponse> buscarNota(@PathVariable Long id){
        return notasService.buscaNotaPorId(id);
    }
    @GetMapping(value = "buscar/{idTurma}/{idMateria}")
    public ResponseEntity<DefaultResponse> buscarNotaPorTurma(@PathVariable Long idTurma, @PathVariable Long idMateria){
        return notasService.buscaNotasPorTurmaAMateria(idTurma, idMateria);
    }
}
