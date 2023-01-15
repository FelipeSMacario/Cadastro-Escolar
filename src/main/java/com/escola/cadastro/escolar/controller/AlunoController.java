package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.AlunoApi;
import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "alunos")
public class AlunoController implements AlunoApi {

    @Autowired
    PessoaService alunoService;

    private final String cargo = "Aluno";

    @GetMapping(value = "/listar")
    public ResponseEntity listarAlunos(){
        return alunoService.listar(cargo);
    }

    @GetMapping(value = "/buscar/{matricula}")
    public ResponseEntity buscarAluno(@PathVariable Long matricula) {
        return alunoService.buscar(matricula, cargo);
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarAlunos(@RequestBody Pessoa pessoa) {
        return alunoService.cadastrar(pessoa, cargo);
    }

    @PutMapping(value = "/atualizar")
    public ResponseEntity atualizarAluno(@RequestBody EntradaDTO entradaDTO) {
        return alunoService.atualizar(entradaDTO, cargo);
    }

    @DeleteMapping(value = "/deletar/{matricula")
    public ResponseEntity deletarAluno(@PathVariable Long matricula) {
        return alunoService.deletar(matricula, cargo);
    }

}
