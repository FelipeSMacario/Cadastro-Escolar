package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.dto.EntradaQuadroHorarioDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Salas")
public interface SalaApi {
    @ApiOperation(("buscar salas"))
    public ResponseEntity listarSalas();

    @ApiOperation("Buscar um professor")
    public ResponseEntity buscarSalas(@PathVariable Long id);
}
