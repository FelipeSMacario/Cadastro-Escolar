package com.example.Turma.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Turma.model.response.DefaultResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Salas")
public interface SalaApi {
    @ApiOperation("listar salas")
    public ResponseEntity<DefaultResponse> listarSalas();

    @ApiOperation("Buscar sala por idr")
    public ResponseEntity<DefaultResponse> buscarSalas(@PathVariable Long id);
}
