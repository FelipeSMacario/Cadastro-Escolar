package com.example.Turma.service;

import com.example.Turma.exception.AlunoNoRegisterException;
import com.example.Turma.exception.TurmaNotFoundException;
import com.example.Turma.exception.UserNotFoundException;
import com.example.Turma.model.Pessoa;
import com.example.Turma.model.Turma;
import com.example.Turma.repository.PessoaRepository;
import com.example.Turma.repository.TurmaAlunoRepository;
import com.example.Turma.repository.TurmaRepository;
import com.example.Turma.service.enums.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidacoesService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaAlunoRepository turmaAlunoRepository;

    Pessoa buscaPessoaSemStatus(Long matricula, String cargo){
        return pessoaRepository.findByMatriculaAndCargo(matricula, cargo).orElseThrow(() -> new UserNotFoundException(matricula));
    }

    Pessoa buscaPessoa(Long matricula, String cargo){
        return pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo").orElseThrow(() -> new UserNotFoundException(matricula));
    }

    Turma buscaTurmaPorNumero(int numero){
        return turmaRepository.findByNumero(numero).orElseThrow(() -> new TurmaNotFoundException(numero));
    }

    Turma buscaTurma(Long id){
        return turmaRepository.findById(id).orElseThrow(() -> new TurmaNotFoundException(id));
    }

    Long buscaTurmaPorMatricula(Long matricula){
        Long id = turmaAlunoRepository.buscaTurmaPorMatricula(matricula).orElseThrow(() -> new AlunoNoRegisterException(matricula));
        if (id == 0)
            throw new AlunoNoRegisterException(matricula);
        return id;
    }


    List<Pessoa> validaPessoa(List<Long> matricula) {
        List<Pessoa> pessoas = new ArrayList<>();

        matricula.forEach(valor -> {
            Pessoa pessoa = buscaPessoaSemStatus(valor, Cargo.Aluno.toString());

            if (pessoa.getStatus().equals("Ativo")) {
                pessoas.add(pessoa);
            }
        });


        return pessoas;
    }
}
