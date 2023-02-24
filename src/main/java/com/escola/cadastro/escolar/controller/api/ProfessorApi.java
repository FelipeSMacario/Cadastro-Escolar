package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Professor")
public interface ProfessorApi {
    @ApiOperation(("Listar professores"))
    public ResponseEntity listarProfessores();

    @ApiOperation("Buscar um professor")
    public ResponseEntity<DefaultResponse> buscarProfessor(@PathVariable Long matricula);

    public ResponseEntity buscarAlunoPorNome(@PathVariable String nome);

    @ApiOperation("Cadastrar um novo professor")
    public ResponseEntity<DefaultResponse> cadastrarProfessor(@RequestBody @Valid Pessoa pessoa);

    @ApiOperation("Atualizado dados do professor")
    public ResponseEntity<DefaultResponse> atualizarProfessor(@RequestBody EntradaDTO entradaDTO);

}
