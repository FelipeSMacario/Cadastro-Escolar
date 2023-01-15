package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.ProfessorApi;
import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "professor")
public class ProfessorController implements ProfessorApi {
    @Autowired
    PessoaService professorService;

    private final String cargo = "Professor";

    @GetMapping()
    public ResponseEntity listarProfessores() {
        return professorService.listar(cargo);
    }

    @GetMapping(value = "/buscar/{matricula}")
    public ResponseEntity buscarProfessor(@PathVariable Long matricula) {
        return professorService.buscar(matricula, cargo);
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarProfessor(@RequestBody Pessoa pessoa) {
        return professorService.cadastrar(pessoa, cargo);
    }

    @PutMapping(value = "/atualizar")
    public ResponseEntity atualizarProfessor(@RequestBody EntradaDTO entradaDTO) {
        return professorService.atualizar(entradaDTO, cargo);
    }

    @DeleteMapping(value = "/deletar/{matricula")
    public ResponseEntity deletarProfessor(@PathVariable Long matricula) {
        return professorService.deletar(matricula, cargo);
    }
}
