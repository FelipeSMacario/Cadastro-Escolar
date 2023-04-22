package com.example.aulas.controller.api;

import com.example.aulas.dto.EntradaQuadroAtualizarDTO;
import com.example.aulas.dto.EntradaQuadroHorarioDTO;
import com.example.aulas.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;


@Api(tags = "Aulas escolares")
public interface QuadroHorarioApi {
    @ApiOperation("Cadastrar aulas")
    ResponseEntity<DefaultResponse> cadastrarHorario(@RequestBody @Valid EntradaQuadroHorarioDTO entrada);

    @ApiIgnore
    ResponseEntity<DefaultResponse> buscarHoraLivrees(@PathVariable Long dia, @PathVariable Long turma);

    @ApiOperation("Busca aulas por turma")
    ResponseEntity<DefaultResponse> buscarHorarioPorTurma(@PathVariable Long turma, @PageableDefault(size = 6, page = 0, sort = {"dia", "horas"}) Pageable pageable);

    @ApiOperation("Buscar aulas por matricula")
    ResponseEntity<DefaultResponse> buscaHoraPorMatricula(@PathVariable Long matricula, @PageableDefault(size = 6, page = 0, sort = {"dia", "horas"}) Pageable pageable);

    @ApiIgnore
    ResponseEntity<DefaultResponse> buscaHoraPorMatriculaSemPaginacao(@PathVariable Long matricula, @PathVariable String cargo);

    @ApiOperation("Buscar aulas por id")
    ResponseEntity<DefaultResponse> buscaHorarioPorId(@PathVariable Long id);

    @ApiOperation("Atualiza aulas")
    ResponseEntity<DefaultResponse> atualizarQuadro(@RequestBody EntradaQuadroAtualizarDTO entrada);

    @ApiOperation("Deletar aulas")
    ResponseEntity<DefaultResponse> deletarQUadro(@PathVariable Long id);
    @ApiOperation("Buscar aulas por matéria")
    public ResponseEntity<DefaultResponse> filtraMateria(@PathVariable Long dia, @PathVariable Long hora);
}
