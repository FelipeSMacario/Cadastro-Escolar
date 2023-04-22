package com.example.Turma.controller.api;

import com.example.Turma.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Turma.dto.AlunoTurmaDTO;
import com.example.Turma.dto.EntradaTurmaAlunoDTO;

@Api(tags = "Turma Aluno")
public interface TurmaAlunos {

    @ApiOperation("cadastrar aluno a turma")
    ResponseEntity cadastrarAlunoTurma(@RequestBody @Valid EntradaTurmaAlunoDTO entrada);
    @ApiOperation("listar alunos e turmas")
    ResponseEntity listarAlunosTurma();

    @ApiOperation("buscar por número da turma")
    ResponseEntity listarAlunosPorNumero(@PathVariable int numero, @PageableDefault(size = 5) Pageable pageable);

    @ApiOperation("listar por nome do aluno")
    ResponseEntity listarturmaAlunoPorNome(@PathVariable String nome);

    @ApiOperation("listar por matricula do aluno")
    ResponseEntity listarTurmasAlunos(@PathVariable Long matricula);

    @ApiOperation("listar por ano da turma")
    ResponseEntity listarAlunosPorAno(@PathVariable Integer ano);

    @ApiOperation("listar turmas por matricula do aluno")
    ResponseEntity buscaTurmaPorMatricula(@PathVariable Long matricula);

    @ApiOperation("listar turmas por matricula e turma")
    ResponseEntity buscaTurmaAlunoPorMatriculaRTurma(@PathVariable Long matricula, @PathVariable Long turma);

    @ApiOperation("Deletar vinculo")
    ResponseEntity deletarAlunoTurma(@PathVariable Long matricula, @PathVariable Long id);

    @ApiOperation("Atualizar vinculo")
    ResponseEntity<DefaultResponse> atualizarAlunoTurma(@RequestBody @Valid AlunoTurmaDTO entradaTurmaAlunoDTO);

    @ApiOperation("listar alunos por turma")
    ResponseEntity buscaAlunoPorTurma(@PathVariable Long id);

    @ApiOperation("listar turma por id")
    ResponseEntity buscarTurmaPorId(@PathVariable Long id);
}
