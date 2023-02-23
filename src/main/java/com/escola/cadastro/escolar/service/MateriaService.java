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
    PessoaRepository pessoaRepository;

    public ResponseEntity listarMaterias() {
        return ResponseEntity.status(HttpStatus.OK).body(materiaRepository.findAll());
    }

    public ResponseEntity buscarMateriaPorNome(String nome) {
        Materia materia = buscaMateriaPorNome(nome);
        return ResponseEntity.ok().body(materia);

    }

    public ResponseEntity<DefaultResponse> cadastrarMateria(Materia materia) {
        Pessoa pessoa = buscaPessoa(materia.getProfessor().getMatricula(), "Professor");

        Materia materiaValidada =  materiaRepository.save(new Materia(null, materia.getNome(), pessoa, null));

        return ResponseEntity.ok().body(DefaultResponse.builder()
                        .success(true)
                        .messagem("Matéria cadastrada com sucesso")
                        .status(HttpStatus.CREATED)
                        .data(materiaValidada)
                .build());
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

    public ResponseEntity<DefaultResponse> deletarMateria(Long id) {
        Materia materia = buscaMateriaPorId(id);
        materiaRepository.deleteById(materia.getId());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                                        .success(true)
                        .messagem("Matéria deletada com sucesso")
                        .status(HttpStatus.OK)
                .build());
    }

    private Materia buscaMateriaPorNome(String nome) {
        return materiaRepository.findByNome(nome).orElseThrow(() -> new MateriaNotFoundException(nome));
    }

    private Materia buscaMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElseThrow(() -> new MateriaNotFoundException("",id));
    }

    private Pessoa buscaPessoa(Long matricula, String cargo){
        return pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo").orElseThrow(() -> new UserNotFoundException(matricula));
    }

}
