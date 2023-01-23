package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.MateriaApi;
import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "materias")
public class MateriaController implements MateriaApi {

    @Autowired
    MateriaService materiaService;

    @GetMapping(value = "/listar")
    public ResponseEntity listarMaterias() {
        return materiaService.listarMaterias();
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastraMateria(@RequestBody Materia materia) {
        return materiaService.cadastrarMateria(materia);
    }

    @GetMapping(value = "/buscar/{nome}")
    public ResponseEntity buscarMateria(@PathVariable String nome) {
        return materiaService.buscarMateriaPorNome(nome);
    }

    @PutMapping(value = "/atualizar")
    public ResponseEntity atualizaMateria(@RequestBody Materia materia) {
        return materiaService.atualizarMateria(materia);
    }

    @DeleteMapping(value = "deletar/{id}")
    public ResponseEntity deletarMateria(@PathVariable Long id) {
        return materiaService.deletarMateria(id);
    }
}
