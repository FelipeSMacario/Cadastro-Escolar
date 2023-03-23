package com.example.aulas.controller.api;

import com.example.aulas.dto.NotasDTO;
import com.example.aulas.dto.NotasTrimestreDTO;
import com.example.aulas.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Notas")
public interface NotasApi {

    @ApiOperation("Cadastrar notas")
    public ResponseEntity<DefaultResponse> cadastrarNotas(@RequestBody NotasDTO notasDTO);

    @ApiOperation("Alterar notas")
    public ResponseEntity<DefaultResponse> alterarNotas(@RequestBody NotasTrimestreDTO notasDTO);

    @ApiOperation("Buscar notas por matricula")
    public ResponseEntity<DefaultResponse> buscarPorMatricula(@PathVariable Long matricula);

    @ApiOperation("Buscar notas por id")
    public ResponseEntity<DefaultResponse> buscarNota(@PathVariable Long id);
    @ApiOperation("Buscar notas por turma")
    public ResponseEntity<DefaultResponse> buscarNotaPorTurma(@PathVariable Long idTurma, @PathVariable Long idMateria);

    @ApiOperation("Buscar alunos por turma e matéria")
    ResponseEntity<DefaultResponse> buscarAlunosPorTurma(@PathVariable Long idTurma, @PathVariable Long idMateria);

}
