package com.example.Dias.service;


import com.example.Dias.model.Materia;
import com.example.Dias.model.Pessoa;
import com.example.Dias.model.response.DefaultResponse;
import com.example.Dias.model.response.ResponseMaterias;
import com.example.Dias.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class MateriaService {
    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    ValidacoesService validacoesService;

    public ResponseEntity<DefaultResponse> listarMaterias(Pageable pageable) {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data((Serializable) materiaRepository.findAll(pageable))
                .build());
    }

    public ResponseEntity<DefaultResponse> buscarMateriaPorNome(String nome) {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(validacoesService.buscaMateriaPorNome(nome))
                .build());

    }

    public ResponseEntity<DefaultResponse> cadastrarMateria(Materia materia) {
        Pessoa pessoa = validacoesService.buscaPessoa(materia.getProfessor().getMatricula(), "Professor");

        Materia materiaValidada =  materiaRepository.save(new Materia(null, materia.getNome(), pessoa));

        return ResponseEntity.ok().body(DefaultResponse.builder()
                        .success(true)
                        .messagem("Matéria cadastrada com sucesso")
                        .status(HttpStatus.CREATED)
                        .data(materiaValidada)
                .build());
    }

    public ResponseEntity<DefaultResponse> atualizarMateria(Materia materia) {
        Materia materia1 = validacoesService.buscaMateriaPorId(materia.getId());
        Materia materiaAtualizada = materiaRepository.save(
                Materia.builder()
                        .id(materia1.getId())
                        .nome(materia.getNome())
                        .professor(materia.getProfessor())
                        .build());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Matéria atualizada com sucesso")
                .status(HttpStatus.OK)
                .data(materiaAtualizada)
                .build());
    }

    public ResponseEntity<DefaultResponse> deletarMateria(Long id) {
        Materia materia = validacoesService.buscaMateriaPorId(id);
        materiaRepository.deleteById(materia.getId());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                                        .success(true)
                        .messagem("Matéria deletada com sucesso")
                        .status(HttpStatus.OK)
                .build());
    }

    public ResponseEntity<DefaultResponse> listarMateriasSemPaginacao() {
        List<Materia> materias = materiaRepository.findAll();
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data((Serializable) materias)
                .build());
    }
}
