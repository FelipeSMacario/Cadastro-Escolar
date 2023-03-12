package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Alunos")
public interface AlunoApi {
    @ApiOperation("Listar alunos")
    public  ResponseEntity<DefaultResponse> listarAlunos();

    @ApiOperation("Buscar um aluno por matricula")
    public  ResponseEntity<DefaultResponse> buscarAluno(@PathVariable Long matricula);

    @ApiOperation("Buscar um aluno por nome")
    public  ResponseEntity<DefaultResponse> buscarAlunoNome(@PathVariable String nome);

    @ApiOperation("Cadastrar um novo aluno")
    public  ResponseEntity<DefaultResponse> cadastrarAlunos(@RequestBody Pessoa pessoa);

    @ApiOperation("Atualizado dados do aluno")
    public  ResponseEntity<DefaultResponse> atualizarAluno(@RequestBody EntradaDTO entradaDTO);
}
