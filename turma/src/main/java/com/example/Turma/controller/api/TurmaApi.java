package com.example.Turma.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.Turma.model.response.DefaultResponse;

@Api(tags = "Turma")
public interface TurmaApi {

    @ApiOperation("listar turmas")
    ResponseEntity<DefaultResponse> listarTurmas();

    @ApiOperation("buscar turma por número")
    ResponseEntity<DefaultResponse> buscarTurmaPorNumero(@PathVariable int numero);

    @ApiOperation("buscar turma por ano")
    ResponseEntity<DefaultResponse> buscarTurmaPorAno(@PathVariable int ano);

}
