package com.example.Dias.controller.api;


import com.example.Dias.model.Materia;
import com.example.Dias.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;


@Api(tags = "Materias escolares")
public interface MateriaApi {
    @ApiOperation("Listar matérias")
    ResponseEntity<DefaultResponse> listarMaterias(@PageableDefault(size = 5, page = 0, sort = "nome") Pageable pageable);

    @ApiIgnore
    ResponseEntity<DefaultResponse> listarMateriasSemPaginacao();

    @ApiOperation("Cadastrar uma nova matéria")
    ResponseEntity<DefaultResponse> cadastraMateria(@RequestBody @Valid Materia materia);

    @ApiOperation("Buscar uma matéria por nome")
    ResponseEntity<DefaultResponse> buscarMateria(@PathVariable String nome);

    @ApiOperation("Atualizado dados da matéria")
    ResponseEntity<DefaultResponse> atualizaMateria(@RequestBody @Valid Materia materia);

     @ApiOperation("Deletar uma matéria por nome")
     ResponseEntity<DefaultResponse> deletarMateria(@PathVariable Long id);
}
