package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
@Api(tags = "Horas disponíveis")
public interface HorasApi {
    @ApiOperation("Listar horas disponíveis")
    public ResponseEntity<DefaultResponse> listarHoras();
}
