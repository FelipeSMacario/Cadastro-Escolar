package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Professor")
public interface ProfessorApi {
    @ApiOperation(("Listar professores"))
    public ResponseEntity listarProfessores();

    @ApiOperation("Buscar um professor")
    public ResponseEntity buscarProfessor(@PathVariable Long matricula);

    @ApiOperation("Cadastrar um novo professor")
    public ResponseEntity cadastrarProfessor(@RequestBody Pessoa pessoa);

    @ApiOperation("Atualizado dados do professor")
    public ResponseEntity atualizarProfessor(@RequestBody EntradaDTO entradaDTO);

}
