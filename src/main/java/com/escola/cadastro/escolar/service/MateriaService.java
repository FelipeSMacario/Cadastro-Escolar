package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.exception.MateriaNotFoundException;
import com.escola.cadastro.escolar.exception.UserNotFoundException;
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
        Materia materia = buscaMateriaPorNome(nome);
        return ResponseEntity.ok().body(materia);

    }

    public ResponseEntity cadastrarMateria(Materia materia) {
        return pessoaRepository.findByMatriculaAndCargoAndStatus(materia.getProfessor().getMatricula(), "Professor", "Ativo")
                .map( record -> {
                    materiaRepository.save(materia);
                   return ResponseEntity.status(HttpStatus.CREATED).body("Matéria cadastrada com sucesso");
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    public ResponseEntity atualizarMateria(Materia materia) {
        Materia materia1 = buscaMateriaPorNome(materia.getNome());
        Materia materiaAtualizada = materiaRepository.save(
                Materia.builder()
                        .id(materia1.getId())
                        .nome(materia.getNome())
                        .professor(materia.getProfessor())
                        .build());
                return ResponseEntity.ok().body(materiaAtualizada);
    }

    public ResponseEntity deletarMateria(Long id) {
        Materia materia = buscaMateriaPorId(id);
        materiaRepository.deleteById(materia.getId());

        return ResponseEntity.ok().body("Matéria deletada com sucesso");
    }

    private Materia buscaMateriaPorNome(String nome) {
        return materiaRepository.findByNome(nome).orElseThrow(() -> new MateriaNotFoundException(nome));
    }

    private Materia buscaMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElseThrow(() -> new MateriaNotFoundException("",id));
    }

}
