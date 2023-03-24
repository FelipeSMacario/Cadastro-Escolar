package com.example.Dias.service;


import com.example.Dias.exception.MateriaNotFoundException;
import com.example.Dias.exception.UserNotFoundException;
import com.example.Dias.model.Materia;
import com.example.Dias.model.Pessoa;
import com.example.Dias.repository.DiasRepositry;
import com.example.Dias.repository.HoraRepository;
import com.example.Dias.repository.MateriaRepository;
import com.example.Dias.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidacoesService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MateriaRepository materiaRepository;


    Pessoa buscaPessoa(Long matricula, String cargo){
        return pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo").orElseThrow(() -> new UserNotFoundException(matricula));
    }

    Materia buscaMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElseThrow(() -> new MateriaNotFoundException("", id));
    }

    Materia buscaMateriaPorNome(String nome) {
        return materiaRepository.findByNome(nome).orElseThrow(() -> new MateriaNotFoundException(nome));
    }

}
