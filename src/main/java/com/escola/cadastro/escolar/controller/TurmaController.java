package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.TurmaApi;
import com.escola.cadastro.escolar.dto.EntradaTurmaAlunoDTO;
import com.escola.cadastro.escolar.model.Turma;
import com.escola.cadastro.escolar.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "turma")
public class TurmaController implements TurmaApi {

    @Autowired
    TurmaService turmaService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarTurma(@RequestBody Turma turma) {
        return turmaService.cadastrarCurso(turma);
    }

    @GetMapping(value = "/listar")
    public ResponseEntity listarTurmas(){
        return turmaService.listarTurmas();
    }

    @GetMapping(value = "/buscar/porNumero/{numero}")
    public ResponseEntity buscarCursoPorNumero(@PathVariable int numero){
        return turmaService.buscaPorNumero(numero);
    }

    @GetMapping(value = "/buscar/porAno/{ano}")
    public ResponseEntity buscarCursoPorAno(@PathVariable int ano){
        return turmaService.buscaPorAno(ano);
    }

    @PutMapping(value = "atualizar")
    public ResponseEntity atualizaTurma(@RequestBody Turma turma){
        return turmaService.atualizaTurma(turma);
    }
}
