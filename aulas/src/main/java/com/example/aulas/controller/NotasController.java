package com.example.aulas.controller;

import com.example.aulas.controller.api.NotasApi;
import com.example.aulas.dto.NotasDTO;
import com.example.aulas.dto.NotasTrimestreDTO;
import com.example.aulas.response.DefaultResponse;
import com.example.aulas.service.NotasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "notas")
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

    @GetMapping(value = "buscarAlunos/{idTurma}/{idMateria}")
    public ResponseEntity<DefaultResponse> buscarAlunosPorTurma(@PathVariable Long idTurma, @PathVariable Long idMateria){
        return notasService.buscarAlunosPorTurma(idTurma, idMateria);
    }
}
