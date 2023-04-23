package com.example.aulas.service;

import com.example.aulas.enums.Cargo;
import com.example.aulas.exception.*;
import com.example.aulas.model.*;
import com.example.aulas.repository.*;
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
    private HoraRepository horaRepository;

    @Autowired
    private DiasRepositry diasRepositry;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private AulaRepository quadroHorarioRepository;

    @Autowired
    private NotasRepository notasRepository;

    @Autowired
    private TurmaAlunoRepository turmaAlunoRepository;

    public Pessoa buscaPessoaSemStatus(Long matricula, String cargo){
        return pessoaRepository.findByMatriculaAndCargo(matricula, cargo).orElseThrow(() -> new UserNotFoundException(matricula));
    }

    public Pessoa buscaPessoa(Long matricula, String cargo){
        return pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo").orElseThrow(() -> new UserNotFoundException(matricula));
    }

    public Turma buscaTurma(Long id){
        return turmaRepository.findById(id).orElseThrow(() -> new TurmaNotFoundException(id));
    }

    public Long buscaTurmaPorMatricula(Long matricula){
        Long id = turmaAlunoRepository.buscaTurmaPorMatricula(matricula).orElseThrow(() -> new AlunoNoRegisterException(matricula));
        if (id == 0)
            throw new AlunoNoRegisterException(matricula);
        return id;
    }
    public Horas buscaHora(Long id) {
        return horaRepository.findById(id).orElseThrow(() -> new HoraNotFoundException(id));
    }

    public Dia buscaDia(Long id) {
        return diasRepositry.findById(id).orElseThrow(() -> new DiaNotFoundException(id));
    }

    public Materia buscaMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElseThrow(() -> new MateriaNotFoundException("", id));
    }

    public Materia buscaMateriaPorNome(String nome) {
        return materiaRepository.findByNome(nome).orElseThrow(() -> new MateriaNotFoundException(nome));
    }
    public QuadroHorario buscaQuadroHorario(Long id) {
        return quadroHorarioRepository.findById(id).orElseThrow(() -> new QuadroHorarioNotFound(id));
    }
    public Notas buscaNotas(Long id){
        return notasRepository.findById(id).orElseThrow(() -> new NotasNotFoundException(id));
    }

    public List<Pessoa> validaPessoa(List<Long> matricula) {
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
