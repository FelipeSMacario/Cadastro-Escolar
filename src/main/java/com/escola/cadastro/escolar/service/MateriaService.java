package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.exception.MateriaNotFoundException;
import com.escola.cadastro.escolar.exception.UserNotFoundException;
import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.repository.MateriaRepository;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MateriaService {
    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    ValidacoesService validacoesService;

    public ResponseEntity listarMaterias() {
        return ResponseEntity.status(HttpStatus.OK).body(materiaRepository.findAll());
    }

    public ResponseEntity<DefaultResponse> buscarMateriaPorNome(String nome) {
        Materia materia = validacoesService.buscaMateriaPorNome(nome);

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(materia)
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

}
