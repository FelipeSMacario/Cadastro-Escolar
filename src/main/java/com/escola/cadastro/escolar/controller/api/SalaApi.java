package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.dto.EntradaQuadroHorarioDTO;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Salas")
public interface SalaApi {
    @ApiOperation("listar salas")
    public ResponseEntity<DefaultResponse> listarSalas();

    @ApiOperation("Buscar sala por idr")
    public ResponseEntity<DefaultResponse> buscarSalas(@PathVariable Long id);
}
