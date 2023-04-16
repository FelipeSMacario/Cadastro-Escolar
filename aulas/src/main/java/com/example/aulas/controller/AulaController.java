package com.example.aulas.controller;

import com.example.aulas.dto.EntradaQuadroAtualizarDTO;
import com.example.aulas.dto.EntradaQuadroHorarioDTO;
import com.example.aulas.response.DefaultResponse;
import com.example.aulas.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "quadroHorario")
public class AulaController {
    @Autowired
    AulaService quadroHorarioService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<DefaultResponse> cadastrarHorario(@RequestBody @Valid EntradaQuadroHorarioDTO entrada){
        return quadroHorarioService.cadastrarSala(entrada);
    }

    @GetMapping(value = "buscar/horasPorDia/{dia}/{turma}")
    public ResponseEntity<DefaultResponse> buscarHoraLivrees(@PathVariable Long dia, @PathVariable Long turma){
        return quadroHorarioService.buscarHorasPorDia(dia, turma);
    }
    @GetMapping(value = "buscar/horariosPorTurma/{turma}")
    public ResponseEntity<DefaultResponse> buscarHorarioPorTurma(@PathVariable Long turma, @PageableDefault(size = 6, page = 0, sort = {"dia", "horas"}) Pageable pageable){
        return quadroHorarioService.buscarHorarioPorTurma(turma, pageable);
    }

    @GetMapping(value = "buscar/HorarioPorMatricula/{matricula}")
    public ResponseEntity<DefaultResponse> buscaHoraPorMatricula(@PathVariable Long matricula, @PageableDefault(size = 6, page = 0, sort = {"dia", "horas"}) Pageable pageable){
        return quadroHorarioService.buscarHorarioPorMatricula(matricula, pageable);
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
