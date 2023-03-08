package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.AlunoApi;
import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.enums.Cargo;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "alunos")
public class AlunoController implements AlunoApi {

    @Autowired
    PessoaService alunoService;
    @GetMapping(value = "/listar")
    public  ResponseEntity listarAlunos(){
        return alunoService.listar(Cargo.Aluno.toString());
    }

    @GetMapping(value = "/buscar/{matricula}")
    public  ResponseEntity<DefaultResponse> buscarAluno(@PathVariable Long matricula) {
        return alunoService.buscar(matricula, Cargo.Aluno.toString());
    }

    @GetMapping(value = "/buscar/porNome/{nome}")
    public  ResponseEntity buscarAlunoNome(@PathVariable String nome) {
        return alunoService.buscarPorNome(nome, Cargo.Aluno.toString());
    }

    @PostMapping(value = "/cadastrar")
    public  ResponseEntity<DefaultResponse> cadastrarAlunos(@RequestBody @Valid  Pessoa pessoa) {
        return alunoService.cadastrar(pessoa, Cargo.Aluno.toString());
    }

    @PutMapping(value = "/atualizar")
    public  ResponseEntity<DefaultResponse> atualizarAluno(@RequestBody EntradaDTO entradaDTO) {
        return alunoService.atualizar(entradaDTO, Cargo.Aluno.toString());
    }
}
