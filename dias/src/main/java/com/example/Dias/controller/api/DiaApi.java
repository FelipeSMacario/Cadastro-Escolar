package com.example.Dias.controller.api;

import com.example.Dias.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

@Api(tags = "Dias da semana")
public interface DiaApi {
    @ApiOperation("Listar dias da semana")
    public ResponseEntity<DefaultResponse> listarDias();
}
