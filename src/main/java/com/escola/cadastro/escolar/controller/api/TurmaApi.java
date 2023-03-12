package com.escola.cadastro.escolar.controller.api;


import com.escola.cadastro.escolar.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Api(tags = "Turma")
public interface TurmaApi {

    @ApiOperation("listar turmas")
    public ResponseEntity<DefaultResponse> listarTurmas();

    @ApiOperation("buscar turma por número")
    public ResponseEntity<DefaultResponse> buscarTurmaPorNumero(@PathVariable int numero);

    @ApiOperation("buscar turma por ano")
    public ResponseEntity<DefaultResponse> buscarTurmaPorAno(@PathVariable int ano);

}
