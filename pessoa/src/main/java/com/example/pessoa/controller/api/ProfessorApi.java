package com.example.pessoa.controller.api;


import com.example.pessoa.dto.EntradaDTO;
import com.example.pessoa.model.Pessoa;
import com.example.pessoa.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


@Api(tags = "Professor")
public interface ProfessorApi {
    @ApiOperation(("Listar professores"))
    ResponseEntity<DefaultResponse> listarProfessores(@PageableDefault(size = 5, page = 0, sort = "matricula") Pageable pageable);

    @ApiIgnore
    ResponseEntity<DefaultResponse> listarSemPaginacao();

    @ApiOperation("Buscar um professor por matricula")
    ResponseEntity<DefaultResponse> buscarProfessor(@PathVariable Long matricula);

    @ApiOperation("Buscar um professor por nome")
    ResponseEntity<DefaultResponse> buscarAlunoPorNome(@PathVariable String nome, @PageableDefault(size = 5, page = 0, sort = "matricula") Pageable pageable);

    @ApiOperation("Cadastrar um novo professor")
    ResponseEntity<DefaultResponse> cadastrarProfessor(@RequestBody @Valid Pessoa pessoa);

    @ApiOperation("Atualizado dados do professor")
    ResponseEntity<DefaultResponse> atualizarProfessor(@RequestBody EntradaDTO entradaDTO);

}
