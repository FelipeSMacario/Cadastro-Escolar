package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.dto.AlunoDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Alunos")
public interface AlunoApi {
    @ApiOperation(("Listar alunos"))
    public ResponseEntity listarAlunos();

    @ApiOperation("Buscar um aluno")
    public ResponseEntity buscarAluno(@PathVariable Long matricula);

    @ApiOperation("Cadastrar um novo aluno")
    public ResponseEntity cadastrarAlunos(@RequestBody Pessoa pessoa);

    @ApiOperation("Atualizado dados do aluno")
    public ResponseEntity atualizarAluno(@RequestBody AlunoDTO alunoDTO);

    @ApiOperation("Deletar aluno")
    public ResponseEntity deletarAluno(@PathVariable Long matricula);
}
