package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.TurmaAlunos;
import com.escola.cadastro.escolar.dto.EntradaTurmaAlunoDTO;
import com.escola.cadastro.escolar.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "turmaAlunos")
public class TurmaAlunoController implements TurmaAlunos {

    @Autowired
    TurmaService turmaService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarAlunoTurma(@RequestBody EntradaTurmaAlunoDTO entrada) {
        return turmaService.cadastrarAlunoTurma(entrada);
    }

    @GetMapping(value = "/buscarTurma/{id}")
    public ResponseEntity listarTurmasAlunosPorId(@PathVariable Long id){
        return turmaService.listarTurmasAlunosPorId(id);
    }

    @GetMapping(value = "/buscarAluno/{matricula}")
    public ResponseEntity listarTurmasAlunos(@PathVariable Long matricula){
        return turmaService.listarTurmaPorMatricula(matricula);
    }

    @DeleteMapping(value = "/removerAluno/{matricula}/{id}")
    public ResponseEntity deletarAlunoTurma(@PathVariable String matricula, @PathVariable String id){
        return turmaService.removerAlunoTurma(Long.parseLong(matricula), Long.parseLong(id));
    }
}
