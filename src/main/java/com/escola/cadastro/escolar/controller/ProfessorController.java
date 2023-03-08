package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.ProfessorApi;
import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.enums.Cargo;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "professor")
public class ProfessorController implements ProfessorApi {
    @Autowired
    PessoaService professorService;

    @GetMapping(value = "/listar")
    public ResponseEntity listarProfessores() {
        return professorService.listar(Cargo.Professor.toString());
    }

    @GetMapping(value = "/buscar/{matricula}")
    public ResponseEntity<DefaultResponse> buscarProfessor(@PathVariable Long matricula) {
        return professorService.buscar(matricula, Cargo.Professor.toString());
    }

    @GetMapping(value = "/buscar/porNome/{nome}")
    public ResponseEntity buscarAlunoPorNome(@PathVariable String nome) {
        return professorService.buscarPorNome(nome, Cargo.Professor.toString());
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<DefaultResponse> cadastrarProfessor(@RequestBody @Valid Pessoa pessoa) {
        return professorService.cadastrar(pessoa, Cargo.Professor.toString());
    }

    @PutMapping(value = "/atualizar")
    public ResponseEntity<DefaultResponse> atualizarProfessor(@RequestBody EntradaDTO entradaDTO) {
        return professorService.atualizar(entradaDTO, Cargo.Professor.toString());
    }



}
