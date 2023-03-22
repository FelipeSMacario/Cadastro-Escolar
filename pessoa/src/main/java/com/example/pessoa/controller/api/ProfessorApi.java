package com.example.pessoa.controller.api;


import com.example.pessoa.dto.EntradaDTO;
import com.example.pessoa.model.Pessoa;
import com.example.pessoa.response.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Api(tags = "Professor")
public interface ProfessorApi {
    @ApiOperation(("Listar professores"))
    public ResponseEntity<DefaultResponse> listarProfessores();

    @ApiOperation("Buscar um professor")
    public ResponseEntity<DefaultResponse> buscarProfessor(@PathVariable Long matricula);

    public ResponseEntity<DefaultResponse> buscarAlunoPorNome(@PathVariable String nome);

    @ApiOperation("Cadastrar um novo professor")
    public ResponseEntity<DefaultResponse> cadastrarProfessor(@RequestBody @Valid Pessoa pessoa);

    @ApiOperation("Atualizado dados do professor")
    public ResponseEntity<DefaultResponse> atualizarProfessor(@RequestBody EntradaDTO entradaDTO);

}
