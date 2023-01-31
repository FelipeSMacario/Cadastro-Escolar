package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.repository.MateriaRepository;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MateriaService {
    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    public ResponseEntity listarMaterias() {
        return ResponseEntity.status(HttpStatus.OK).body(materiaRepository.findAll());
    }

    public ResponseEntity buscarMateriaPorNome(String nome) {
        return materiaRepository.findByNome(nome)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity cadastrarMateria(Materia materia) {
        return pessoaRepository.findByMatriculaAndCargoAndStatus(materia.getProfessor().getMatricula(), "Professor", "Ativo")
                .map( record -> {
                    materiaRepository.save(materia);
                   return ResponseEntity.status(HttpStatus.CREATED).body("Matéria cadastrada com sucesso");
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    public ResponseEntity atualizarMateria(Materia materia) {
        Optional<Materia> materia1 = materiaRepository.findById(materia.getId());

        return materia1
                .map(record -> {
                    Materia materiaAtualizada = materiaRepository.save(
                            Materia.builder()
                                    .id(materia.getId())
                                    .nome(materia.getNome())
                                    .professor(materia.getProfessor())
                                    .build()
                    );
                    return ResponseEntity.ok().body(materiaAtualizada);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity deletarMateria(Long id) {
        Optional<Materia> materia = materiaRepository.findById(id);

        return materia
                .map(record -> {
                    materiaRepository.deleteById(materia.get().getId());
                    return ResponseEntity.status(HttpStatus.OK).body("Matéria deletada com sucesso");
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
