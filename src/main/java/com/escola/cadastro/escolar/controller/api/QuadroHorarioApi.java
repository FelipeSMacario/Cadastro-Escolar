package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.dto.EntradaQuadroAtualizarDTO;
import com.escola.cadastro.escolar.dto.EntradaQuadroHorarioDTO;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Quadro de horários")
public interface QuadroHorarioApi {
    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarHorario(@RequestBody EntradaQuadroHorarioDTO entrada);

    @GetMapping(value = "buscar/horasPorDia/{dia}/{sala}")
    public ResponseEntity buscarHoraLivrees(@PathVariable Long dia, @PathVariable Long sala);
    @PutMapping(value = "atualizar")
    public ResponseEntity atualizarQuadro(@RequestBody EntradaQuadroAtualizarDTO entrada);
    @DeleteMapping(value = "deletar/{id}")
    public ResponseEntity deletarQUadro(@PathVariable Long id);
}
