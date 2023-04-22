package com.example.Dias.controller.api;

import com.example.Dias.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
@Api(tags = "Horas")
public interface HorasApi {
    @ApiOperation("Listar horas disponíveis")
    public ResponseEntity<DefaultResponse> listarHoras();
}
