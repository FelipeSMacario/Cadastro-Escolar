package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MateriaService {
    @Autowired
    MateriaRepository materiaRepository;

    public ResponseEntity listarMaterias() {
        return ResponseEntity.status(HttpStatus.OK).body(materiaRepository.findAll());
    }

    public ResponseEntity buscarMateriaPorNome(String nome) {
        return materiaRepository.findByNome(nome)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity cadastrarMateria(Materia materia) {
        materiaRepository.save(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body("Matéria cadastrada com sucesso");
    }

    public ResponseEntity atualizarMateria(Materia materia) {
        Optional<Materia> materia1 = materiaRepository.findById(materia.getId());

        return materia1
                .map(record -> {
                    Materia materiaAtualizada = materiaRepository.save(
                            Materia.builder()
                                    .id(materia.getId())
                                    .nome(materia.getNome())
                                    .build()
                    );
                    return ResponseEntity.ok().body(materiaAtualizada);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity deletarMateria(String nome) {
        Optional<Materia> materia = materiaRepository.findByNome(nome);

        return materia
                .map(record -> {
                    materiaRepository.deleteById(materia.get().getId());
                    return ResponseEntity.status(HttpStatus.OK).body("Matéria deletada com sucesso");
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
