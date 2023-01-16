package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.dto.NotasDTO;
import com.escola.cadastro.escolar.dto.NotasPessoaDTO;
import com.escola.cadastro.escolar.dto.NotasTrimestreDTO;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Notas")
public interface NotasApi {
    public ResponseEntity cadastrarNotas(@RequestBody NotasDTO notasDTO);

    public ResponseEntity alterarNotas(@RequestBody NotasTrimestreDTO notasDTO);

    public ResponseEntity deletarNotas(@RequestBody NotasTrimestreDTO notasDTO);

    public ResponseEntity listarNotas(@RequestBody NotasPessoaDTO notasPessoaDTO);
}
