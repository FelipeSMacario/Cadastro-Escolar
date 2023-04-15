package com.example.pessoa.controller;

import com.example.pessoa.dto.EntradaDTO;
import com.example.pessoa.enums.Cargo;
import com.example.pessoa.model.Pessoa;
import com.example.pessoa.response.DefaultResponse;
import com.example.pessoa.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "professor")
public class ProfessorController {
    @Autowired
    PessoaService professorService;

    @GetMapping(value = "/listar")
    public ResponseEntity<DefaultResponse> listarProfessores(@PageableDefault(size = 5, page = 0, sort = "matricula") Pageable pageable) {
        return professorService.listar(Cargo.Professor.toString(), pageable);
    }

    @GetMapping(value = "/buscar/{matricula}")
    public ResponseEntity<DefaultResponse> buscarProfessor(@PathVariable Long matricula) {
        return professorService.buscar(matricula, Cargo.Professor.toString());
    }

    @GetMapping(value = "/buscar/porNome/{nome}")
    public ResponseEntity<DefaultResponse> buscarAlunoPorNome(@PathVariable String nome, @PageableDefault(size = 5, page = 0, sort = "matricula") Pageable pageable) {
        return professorService.buscarPorNome(nome, Cargo.Professor.toString(), pageable);
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
