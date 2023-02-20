package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EntradaTurmaAlunoDTO;
import com.escola.cadastro.escolar.dto.PessoaEntradaDTO;
import com.escola.cadastro.escolar.dto.SaidaAlunoTurmaDTO;
import com.escola.cadastro.escolar.dto.SaidaTurmaAlunoDTO;
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

        List<Pessoa> pessoas = validaPessoa(turma.get().getAno(), entrada.getPessoas());

        cadastraAlunos(pessoas, turma.get().getId());


        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso!");
    }

    private void cadastraAlunos(List<Pessoa> pessoas, Long turmaId) {
        pessoas.forEach(p -> {
            turmaRepository.cadastrarTurmaAluno(turmaRepository.buscaIdMaximo(), turmaId, p.getMatricula());
        });
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

    public ResponseEntity buscaPorAno(int ano) {
        return ResponseEntity.ok().body(turmaRepository.findByAno(ano));
    }

    private List<Pessoa> validaPessoa(Integer ano, List<PessoaEntradaDTO> matricula) {
        List<Pessoa> pessoas = new ArrayList<>();

        matricula.forEach(valor -> {
            Pessoa pessoa = pessoaRepository.findByMatriculaAndCargoAndStatus(valor.getMatricula(), "Aluno", "Ativo").get();

            if (!pessoa.getAno().equals(ano)) throw new ServiceException("Ano incompativel entre a turma e o aluno");

            pessoas.add(pessoa);
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


    public ResponseEntity listarTurmasAlunosPorId(Long id) {
        Optional<Turma> turma = turmaRepository.findById(id);
        return turma
                .map(record -> {
                    SaidaTurmaAlunoDTO saida = new SaidaTurmaAlunoDTO(turma.get(), turma.get().getAlunos());
                    return ResponseEntity.ok().body(saida);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity listarTurmaPorMatricula(Long matricula) {
        Optional<Pessoa> pessoa = pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, "Aluno", "Ativo");
        Turma turma = pessoa.get().getTurmas().stream().findFirst().orElse(new Turma());
        return pessoa
                .map(record -> {
                    SaidaAlunoTurmaDTO saida = new SaidaAlunoTurmaDTO(pessoa.get(), turma);
                    return ResponseEntity.ok().body(saida);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity removerAlunoTurma(Long matricula, Long id) {
        Optional<Turma> turma = turmaRepository.findById(id);
        Optional<Pessoa> pessoa = turma.get().getAlunos().stream().filter(v -> v.getMatricula().equals(matricula)).findFirst();
        return pessoa
                .map(record -> {
                    turmaRepository.deletaAluno(pessoa.get().getMatricula());
                    return ResponseEntity.status(HttpStatus.OK).body(turma.get());
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity listarAlunosPorAno(Integer ano) {
        List<Pessoa> pessoas = pessoaRepository.findByCargoAndStatusAndAno("Aluno", "Ativo", ano);
        List<Pessoa> pessoasFiltradas = new ArrayList<>();
        pessoas.forEach(p -> {

            if (turmaRepository.validaAluno(p.getMatricula()).get() == 0) pessoasFiltradas.add(p);

        });
        return ResponseEntity.ok().body(pessoasFiltradas);
    }
}

