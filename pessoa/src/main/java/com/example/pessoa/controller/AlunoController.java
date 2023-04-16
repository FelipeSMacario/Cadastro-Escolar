package com.example.pessoa.controller;

import com.example.pessoa.dto.EntradaDTO;
import com.example.pessoa.enums.Cargo;
import com.example.pessoa.model.Pessoa;
import com.example.pessoa.response.DefaultResponse;
import com.example.pessoa.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "alunos")
public class AlunoController {
    @Autowired
    PessoaService alunoService;

    @GetMapping(value = "/listar")
    public ResponseEntity<DefaultResponse> listarAlunos(@PageableDefault(size = 5, page = 0, sort = "matricula") Pageable pageable){
        return alunoService.listar(Cargo.Aluno.toString(), pageable);
    }

    @GetMapping(value = "/buscar/{matricula}")
    public  ResponseEntity<DefaultResponse> buscarAluno(@PathVariable Long matricula) {
        return alunoService.buscar(matricula, Cargo.Aluno.toString());
    }
    @GetMapping(value = "/buscar/porNome/{nome}")
    public  ResponseEntity<DefaultResponse> buscarAlunoNome(@PathVariable String nome, @PageableDefault(size = 5, page = 0, sort = "matricula") Pageable pageable) {
        return alunoService.buscarPorNome(nome, Cargo.Aluno.toString(), pageable);
    }

    @PostMapping(value = "/cadastrar")
    public  ResponseEntity<DefaultResponse> cadastrarAlunos(@RequestBody @Valid Pessoa pessoa) {
        return alunoService.cadastrar(pessoa, Cargo.Aluno.toString());
    }

    @PutMapping(value = "/atualizar")
    public  ResponseEntity<DefaultResponse> atualizarAluno(@RequestBody @Valid EntradaDTO entradaDTO) {
        return alunoService.atualizar(entradaDTO, Cargo.Aluno.toString());
    }
}
