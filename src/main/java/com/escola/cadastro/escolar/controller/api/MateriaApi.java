package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.model.Materia;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Materias")
public interface MateriaApi {
    @ApiOperation("Listar matérias")
    public ResponseEntity listarMaterias();

    @ApiOperation("Cadastrar uma nova matéria")
    public ResponseEntity cadastraMateria(@RequestBody Materia materia);

    @ApiOperation("Buscar uma matéria por nome")
    public ResponseEntity buscarMateria(@PathVariable String nome);

    @ApiOperation("Atualizado dados da matéria")
    public ResponseEntity atualizaMateria(@RequestBody Materia materia);

     @ApiOperation("Deletar uma matéria por nome")
     public ResponseEntity deletarMateria(@PathVariable String nome);
}
