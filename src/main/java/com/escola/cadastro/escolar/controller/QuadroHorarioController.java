package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.QuadroHorarioApi;
import com.escola.cadastro.escolar.dto.EntradaQuadroAtualizarDTO;
import com.escola.cadastro.escolar.dto.EntradaQuadroHorarioDTO;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.service.QuadroHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "quadroHorario")
public class QuadroHorarioController implements QuadroHorarioApi {

    @Autowired
    QuadroHorarioService quadroHorarioService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<DefaultResponse> cadastrarHorario(@RequestBody @Valid EntradaQuadroHorarioDTO entrada){
        return quadroHorarioService.cadastrarSala(entrada);
    }

    @GetMapping(value = "buscar/horasPorDia/{dia}/{turma}")
    public ResponseEntity<DefaultResponse> buscarHoraLivrees(@PathVariable Long dia, @PathVariable Long turma){
        return quadroHorarioService.buscarHorasPorDia(dia, turma);
    }
    @GetMapping(value = "buscar/horariosPorTurma/{turma}")
    public ResponseEntity<DefaultResponse> buscarHorarioPorTurma(@PathVariable Long turma){
        return quadroHorarioService.buscarHorarioPorTurma(turma);
    }

    @GetMapping(value = "buscar/HorarioPorMatricula/{matricula}")
    public ResponseEntity<DefaultResponse> buscaHoraPorMatricula(@PathVariable Long matricula){
        return quadroHorarioService.buscarHorarioPorMatricula(matricula);
    }

    @GetMapping(value = "busca/HorarioPorId/{id}")
    public ResponseEntity<DefaultResponse> buscaHorarioPorId(@PathVariable Long id){
        return quadroHorarioService.buscaHorarioPorId(id);
    }

    @PutMapping(value = "atualizar")
    public ResponseEntity<DefaultResponse> atualizarQuadro(@RequestBody EntradaQuadroAtualizarDTO entrada){
        return quadroHorarioService.atualizarQuadro(entrada);
    }

    @DeleteMapping(value = "deletar/{id}")
    public ResponseEntity<DefaultResponse> deletarQUadro(@PathVariable Long id){
        return quadroHorarioService.deletarQuadro(id);
    }

    @GetMapping(value = "busca/MateriaUsada/{dia}/{hora}")
    public ResponseEntity<DefaultResponse> filtraMateria(@PathVariable Long dia, @PathVariable Long hora){
        return quadroHorarioService.filtrarMaterias(dia, hora);
    }

}
