package com.example.Turma.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Turma.dto.AlunoTurmaDTO;
import com.example.Turma.dto.EntradaTurmaAlunoDTO;

@Api(tags = "Vinculo entre turma e alunos")
public interface TurmaAlunos {

    @ApiOperation("cadastrar aluno a turma")
    public ResponseEntity cadastrarAlunoTurma(@RequestBody EntradaTurmaAlunoDTO entrada);
    @ApiOperation("listar alunos e turmas")
    public ResponseEntity listarAlunosTurma();

    @ApiOperation("buscar por número da turma")
    public ResponseEntity listarAlunosPorNumero(@PathVariable int numero, @PageableDefault(size = 2) Pageable pageable);

    @ApiOperation("listar por nome do aluno")
    public ResponseEntity listarturmaAlunoPorNome(@PathVariable String nome);

    @ApiOperation("listar por matricula do aluno")
    public ResponseEntity listarTurmasAlunos(@PathVariable Long matricula);

    @ApiOperation("listar por ano da turma")
    public ResponseEntity listarAlunosPorAno(@PathVariable Integer ano);

    @ApiOperation("listar turmas por matricula do aluno")
    public ResponseEntity buscaTurmaPorMatricula(@PathVariable Long matricula);

    @ApiOperation("listar turmas por matricula e turma")
    public ResponseEntity buscaTurmaAlunoPorMatriculaRTurma(@PathVariable Long matricula, @PathVariable Long turma);

    @ApiOperation("Deletar vinculo")
    public ResponseEntity deletarAlunoTurma(@PathVariable Long matricula, @PathVariable Long id);

    @ApiOperation("Atualizar vinculo")
    public ResponseEntity atualizarAlunoTurma(@RequestBody AlunoTurmaDTO entradaTurmaAlunoDTO);

    @ApiOperation("listar alunos por turma")
    public ResponseEntity buscaAlunoPorTurma(@PathVariable Long id);

    @ApiOperation("listar turma por id")
    public ResponseEntity buscarTurmaPorId(@PathVariable Long id);
}
