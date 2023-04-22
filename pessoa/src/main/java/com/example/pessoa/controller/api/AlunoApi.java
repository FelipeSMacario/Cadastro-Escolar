package com.example.pessoa.controller.api;


import com.example.pessoa.dto.EntradaDTO;
import com.example.pessoa.model.Pessoa;
import com.example.pessoa.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Alunos")
public interface AlunoApi {
    @ApiOperation("Listar alunos")
    ResponseEntity<DefaultResponse> listarAlunos(@PageableDefault(size = 5, page = 0, sort = "matricula") Pageable pageable);

    @ApiOperation("Buscar um aluno por matricula")
    ResponseEntity<DefaultResponse> buscarAluno(@PathVariable Long matricula);

    @ApiOperation("Buscar um aluno por nome")
    ResponseEntity<DefaultResponse> buscarAlunoNome(@PathVariable String nome, @PageableDefault(size = 5, page = 0, sort = "matricula") Pageable pageable);

    @ApiOperation("Cadastrar um novo aluno")
    ResponseEntity<DefaultResponse> cadastrarAlunos(@RequestBody @Valid Pessoa pessoa);

    @ApiOperation("Atualizado dados do aluno")
    public ResponseEntity<DefaultResponse> atualizarAluno(@RequestBody @Valid EntradaDTO entradaDTO);
}
