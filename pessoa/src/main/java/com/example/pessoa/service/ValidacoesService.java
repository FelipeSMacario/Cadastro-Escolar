package com.example.pessoa.service;

import com.example.pessoa.exceptions.FieldDuplicateException;
import com.example.pessoa.exceptions.UserNotFoundException;
import com.example.pessoa.model.Pessoa;
import com.example.pessoa.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    void buscaPessoaCpf(String cpf){
        List<Pessoa> pessoa = pessoaRepository.findByCpf(cpf);
        if (!pessoa.isEmpty())
            throw  new FieldDuplicateException("O campo CPF já está vinculado a outro usuário");
    }


    public void buscaEmail(String email) {
        List<Pessoa> pessoa = pessoaRepository.findByEmail(email);
        if (!pessoa.isEmpty()) throw  new FieldDuplicateException("O campo Email já está vinculado a outro usuário");
    }

    public void validaAtualizacaoPessoa(Pessoa pessoa) {
        List<Pessoa> pessoaCpf = pessoaRepository.findByCpf(pessoa.getCpf());
        List<Pessoa> pessoaEmail = pessoaRepository.findByEmail(pessoa.getEmail());

        if (!pessoaCpf.isEmpty() && !pessoaCpf.get(0).getMatricula().equals(pessoa.getMatricula())){
            throw  new FieldDuplicateException("O campo CPF já está vinculado a outro usuário");
        }

        if (!pessoaEmail.isEmpty() && !pessoaEmail.get(0).getMatricula().equals(pessoa.getMatricula())){
            throw  new FieldDuplicateException("O campo Email já está vinculado a outro usuário");
        }
    }
}
