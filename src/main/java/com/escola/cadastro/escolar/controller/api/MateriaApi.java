package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Materias escolares")
public interface MateriaApi {
    @ApiOperation("Listar matérias")
    public ResponseEntity<DefaultResponse> listarMaterias();

    @ApiOperation("Cadastrar uma nova matéria")
    public ResponseEntity<DefaultResponse> cadastraMateria(@RequestBody @Valid Materia materia);

    @ApiOperation("Buscar uma matéria por nome")
    public ResponseEntity<DefaultResponse> buscarMateria(@PathVariable String nome);

    @ApiOperation("Atualizado dados da matéria")
    public ResponseEntity<DefaultResponse> atualizaMateria(@RequestBody Materia materia);

     @ApiOperation("Deletar uma matéria por nome")
     public ResponseEntity<DefaultResponse> deletarMateria(@PathVariable Long id);
}
