package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.AlunoApi;
import com.escola.cadastro.escolar.dto.AlunoDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "alunos")
public class AlunoController implements AlunoApi {

    @Autowired
    AlunoService alunoService;

    @GetMapping(value = "/listar")
    public ResponseEntity listarAlunos(){
        return alunoService.listarAlunos();
    }

    @GetMapping(value = "/buscarAluno/{matricula}")
    public ResponseEntity buscarAluno(@PathVariable Long matricula) {
        return alunoService.buscarAluno(matricula);
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarAlunos(@RequestBody Pessoa pessoa) {
        return alunoService.cadastrarAluno(pessoa);
    }

    @PutMapping(value = "/atualizar")
    public ResponseEntity atualizarAluno(@RequestBody AlunoDTO alunoDTO) {
        return alunoService.atualizarAluno(alunoDTO);
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity deletarAluno(@PathVariable Long id) {
        return alunoService.deletarAluno(id);
    }

}
