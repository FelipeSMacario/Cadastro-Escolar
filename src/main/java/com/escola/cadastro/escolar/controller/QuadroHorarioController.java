package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.QuadroHorarioApi;
import com.escola.cadastro.escolar.dto.EntradaQuadroAtualizarDTO;
import com.escola.cadastro.escolar.dto.EntradaQuadroHorarioDTO;
import com.escola.cadastro.escolar.service.QuadroHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "QuadroHorario")
public class QuadroHorarioController implements QuadroHorarioApi {

    @Autowired
    QuadroHorarioService quadroHorarioService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarHorario(@RequestBody EntradaQuadroHorarioDTO entrada){
        return quadroHorarioService.cadastrarSala(entrada);
    }

    @GetMapping(value = "buscar/horasPorDia/{dia}/{sala}")
    public ResponseEntity buscarHoraLivrees(@PathVariable Long dia, @PathVariable Long sala){
        return quadroHorarioService.buscarHorasPorDia(dia, sala);
    }
    @PutMapping(value = "atualizar")
    public ResponseEntity atualizarQuadro(@RequestBody EntradaQuadroAtualizarDTO entrada){
        return quadroHorarioService.atualizarQuadro(entrada);
    }

    @DeleteMapping(value = "deletar/{id}")
    public ResponseEntity deletarQUadro(@PathVariable Long id){
        return quadroHorarioService.deletarQuadro(id);
    }

}
