package com.example.pessoa.service;

import com.example.pessoa.exceptions.UserNotFoundException;
import com.example.pessoa.model.Pessoa;
import com.example.pessoa.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidacoesService {

    @Autowired
    private PessoaRepository pessoaRepository;


    Pessoa buscaPessoa(Long matricula, String cargo){
        return pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo").orElseThrow(() -> new UserNotFoundException(matricula));
    }

    Pessoa buscaPessoaSemCargo(Long matricula){
        return pessoaRepository.findById(matricula).orElseThrow(() -> new UserNotFoundException(matricula));
    }


}
