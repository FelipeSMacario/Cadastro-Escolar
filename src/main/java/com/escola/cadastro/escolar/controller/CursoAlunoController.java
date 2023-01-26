package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.dto.EntradaTurmaAlunoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "cursoAlunos")
public class CursoAlunoController {

    @Autowired
    com.escola.cadastro.escolar.service.TurmaService TurmaService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarAlunoTurma(@RequestBody EntradaTurmaAlunoDTO entrada) {
        return TurmaService.cadastrarAlunoTurma(entrada);
    }
}
