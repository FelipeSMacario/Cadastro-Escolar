package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.dto.EntradaQuadroAtualizarDTO;
import com.escola.cadastro.escolar.dto.EntradaQuadroHorarioDTO;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Aulas escolares")
public interface QuadroHorarioApi {
    @ApiOperation("Cadastrar aulas")
    public ResponseEntity<DefaultResponse> cadastrarHorario(@RequestBody @Valid EntradaQuadroHorarioDTO entrada);

    @ApiOperation("Buscar aula disponiveis")
    public ResponseEntity<DefaultResponse> buscarHoraLivrees(@PathVariable Long dia, @PathVariable Long turma);
    @ApiOperation("Busca aulas por turma")
    public ResponseEntity<DefaultResponse> buscarHorarioPorTurma(@PathVariable Long turma);

    @ApiOperation("Buscar aulas por matricula")
    public ResponseEntity<DefaultResponse> buscaHoraPorMatricula(@PathVariable Long matricula);

    @ApiOperation("Buscar aulas por id")
    public ResponseEntity<DefaultResponse> buscaHorarioPorId(@PathVariable Long id);

    @ApiOperation("Atualiza aulas")
    public ResponseEntity<DefaultResponse> atualizarQuadro(@RequestBody EntradaQuadroAtualizarDTO entrada);

    @ApiOperation("Deletar aulas")
    public ResponseEntity<DefaultResponse> deletarQUadro(@PathVariable Long id);
    @ApiOperation("Buscar aulas por matéria")
    public ResponseEntity<DefaultResponse> filtraMateria(@PathVariable Long dia, @PathVariable Long hora);
}
