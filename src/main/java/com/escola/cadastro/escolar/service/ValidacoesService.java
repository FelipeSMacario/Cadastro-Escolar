package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.exception.*;
import com.escola.cadastro.escolar.model.*;
import com.escola.cadastro.escolar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidacoesService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    HoraRepository horaRepository;

    @Autowired
    DiasRepositry diasRepositry;

    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    QuadroHorarioRepository quadroHorarioRepository;

    @Autowired
    NotasRepository notasRepository;

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
        return turmaRepository.buscaTurmaPorMatricula(matricula).orElseThrow(() -> new AlunoNoRegisterException(matricula));
    }
    Horas buscaHora(Long id) {
        return horaRepository.findById(id).orElseThrow(() -> new HoraNotFoundException(id));
    }

    Dia buscaDia(Long id) {
        return diasRepositry.findById(id).orElseThrow(() -> new DiaNotFoundException(id));
    }

    Materia buscaMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElseThrow(() -> new MateriaNotFoundException("", id));
    }

    Materia buscaMateriaPorNome(String nome) {
        return materiaRepository.findByNome(nome).orElseThrow(() -> new MateriaNotFoundException(nome));
    }
    QuadroHorario buscaQuadroHorario(Long id) {
        return quadroHorarioRepository.findById(id).orElseThrow(() -> new QuadroHorarioNotFound(id));
    }
    Notas buscaNotas(Long id){
        return notasRepository.findById(id).orElseThrow(() -> new NotasNotFoundException(id));
    }
}
