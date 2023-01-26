package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EntradaTurmaAlunoDTO;
import com.escola.cadastro.escolar.dto.PessoaEntradaDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Turma;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import com.escola.cadastro.escolar.repository.TurmaRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {
    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    public ResponseEntity cadastrarAlunoTurma(EntradaTurmaAlunoDTO entrada) {
        Optional<Turma> turma = Optional.ofNullable(turmaRepository.findById(entrada.getTurmaId())).orElseThrow(() -> new ServiceException("Nenhuma turma identificada"));

        List<Pessoa> pessoas = validaPessoa(entrada.getPessoas());;

        turma.get().setAlunos(pessoas);

        turmaRepository.save(turma.get());

        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso!");
    }

    public ResponseEntity listarTurmas() {
        return ResponseEntity.status(HttpStatus.OK).body(turmaRepository.findAll());
    }

    public ResponseEntity buscaPorNumero(int numero) {
        return turmaRepository.findByNumero(numero)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity atualizaTurma(Turma turma) {
        Optional<Turma> turmaAntiga = turmaRepository.findById(turma.getId());

        return turmaAntiga
                .map(record -> {
                    Turma turmaAtualizada = turmaRepository.save(Turma.builder()
                            .id(turma.getId())
                            .numero(turma.getNumero())
                            .ano(turma.getAno())
                            .build()
                    );
                    return ResponseEntity.ok().body(turmaAtualizada);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity buscaPorAno(int ano){
        return ResponseEntity.ok().body(turmaRepository.findByAno(ano));
    }

    private  List<Pessoa> validaPessoa(List<PessoaEntradaDTO> matricula){
        List<Pessoa> pessoas = new ArrayList<>();

            matricula.forEach(valor -> {
                pessoas.add(pessoaRepository.findByMatriculaAndCargoAndStatus(valor.getMatricula(), "Aluno", "Ativo").get());
            });


        return pessoas;
    }

    public ResponseEntity cadastrarCurso(Turma turma) {
        turmaRepository.save(Turma.builder()
                        .ano(turma.getAno())
                        .numero(turma.getNumero())
                .build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastrado com sucesso");
    }



}

