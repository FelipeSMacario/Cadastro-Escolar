package com.example.Turma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Turma.exception.AlunoNoRegisterException;
import com.example.Turma.exception.DiaNotFoundException;
import com.example.Turma.exception.HoraNotFoundException;
import com.example.Turma.exception.MateriaNotFoundException;
import com.example.Turma.exception.NotasNotFoundException;
import com.example.Turma.exception.QuadroHorarioNotFound;
import com.example.Turma.exception.TurmaNotFoundException;
import com.example.Turma.exception.UserNotFoundException;
import com.example.Turma.model.Dia;
import com.example.Turma.model.Materia;
import com.example.Turma.model.Notas;
import com.example.Turma.model.Pessoa;
import com.example.Turma.model.QuadroHorario;
import com.example.Turma.model.Turma;
import com.example.Turma.model.response.Horas;
import com.example.Turma.repository.DiasRepositry;
import com.example.Turma.repository.HoraRepository;
import com.example.Turma.repository.MateriaRepository;
import com.example.Turma.repository.NotasRepository;
import com.example.Turma.repository.PessoaRepository;
import com.example.Turma.repository.QuadroHorarioRepository;
import com.example.Turma.repository.TurmaAlunoRepository;
import com.example.Turma.repository.TurmaRepository;
import com.example.Turma.service.enums.Cargo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidacoesService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private HoraRepository horaRepository;

    @Autowired
    private DiasRepositry diasRepositry;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private QuadroHorarioRepository quadroHorarioRepository;

    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private TurmaAlunoRepository turmaAlunoRepository;

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

    List<Pessoa> validaPessoa(List<Long> matricula) {
        List<Pessoa> pessoas = new ArrayList<>();

        matricula.forEach(valor -> {
            Pessoa pessoa = buscaPessoa(valor, Cargo.Aluno.toString());

            pessoas.add(pessoa);
        });


        return pessoas;
    }
}
