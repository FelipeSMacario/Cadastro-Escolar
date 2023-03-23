package com.example.aulas.controller.api;

import com.example.aulas.dto.EntradaQuadroAtualizarDTO;
import com.example.aulas.dto.EntradaQuadroHorarioDTO;
import com.example.aulas.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


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
