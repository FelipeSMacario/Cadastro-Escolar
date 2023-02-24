package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.AlunoApi;
import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "alunos")
public class AlunoController implements AlunoApi {

    @Autowired
    PessoaService alunoService;

    private final String cargo = "Aluno";

    @GetMapping(value = "/listar")
    public  ResponseEntity listarAlunos(){
        return alunoService.listar(cargo);
    }

    @GetMapping(value = "/buscar/{matricula}")
    public  ResponseEntity<DefaultResponse> buscarAluno(@PathVariable Long matricula) {
        return alunoService.buscar(matricula, cargo);
    }

    @GetMapping(value = "/buscar/porNome/{nome}")
    public  ResponseEntity buscarAlunoNome(@PathVariable String nome) {
        return alunoService.buscarPorNome(nome, cargo);
    }

    @PostMapping(value = "/cadastrar")
    public  ResponseEntity<DefaultResponse> cadastrarAlunos(@RequestBody @Valid  Pessoa pessoa) {
        return alunoService.cadastrar(pessoa, cargo);
    }

    @PutMapping(value = "/atualizar")
    public  ResponseEntity<DefaultResponse> atualizarAluno(@RequestBody EntradaDTO entradaDTO) {
        return alunoService.atualizar(entradaDTO, cargo);
    }
}
