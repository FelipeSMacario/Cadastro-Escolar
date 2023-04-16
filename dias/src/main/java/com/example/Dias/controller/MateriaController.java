package com.example.Dias.controller;


import com.example.Dias.controller.api.MateriaApi;
import com.example.Dias.service.MateriaService;
import com.example.Dias.model.Materia;
import com.example.Dias.model.response.DefaultResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "materias")
public class MateriaController implements MateriaApi {

    @Autowired
    MateriaService materiaService;

    @GetMapping(value = "/listar")
    public ResponseEntity<DefaultResponse> listarMaterias(@PageableDefault(size = 5, page = 0, sort = "nome") Pageable pageable) {
        return materiaService.listarMaterias(pageable);
    }

    @GetMapping(value = "/listarSemPaginacao")
    public ResponseEntity<DefaultResponse> listarMateriasSemPaginacao() {
        return materiaService.listarMateriasSemPaginacao();
    }

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<DefaultResponse> cadastraMateria(@RequestBody @Valid Materia materia) {
        return materiaService.cadastrarMateria(materia);
    }

    @GetMapping(value = "/buscar/{nome}")
    public ResponseEntity<DefaultResponse> buscarMateria(@PathVariable String nome) {
        return materiaService.buscarMateriaPorNome(nome);
    }

    @PutMapping(value = "/atualizar")
    public ResponseEntity<DefaultResponse> atualizaMateria(@RequestBody @Valid Materia materia) {
        return materiaService.atualizarMateria(materia);
    }

    @DeleteMapping(value = "deletar/{id}")
    public ResponseEntity<DefaultResponse> deletarMateria(@PathVariable Long id) {
        return materiaService.deletarMateria(id);
    }
}
