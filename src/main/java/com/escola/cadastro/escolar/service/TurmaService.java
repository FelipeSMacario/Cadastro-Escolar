package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EntradaTurmaAlunoDTO;
import com.escola.cadastro.escolar.dto.PessoaEntradaDTO;
import com.escola.cadastro.escolar.dto.SaidaAlunoTurmaDTO;
import com.escola.cadastro.escolar.dto.SaidaTurmaAlunoDTO;
import com.escola.cadastro.escolar.exception.TurmaNotFoundException;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Turma;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
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

    @Autowired
    ValidacoesService validacoesService;


    public ResponseEntity<DefaultResponse> cadastrarAlunoTurma(EntradaTurmaAlunoDTO entrada) {
        Turma turma = buscaTurma(entrada.getTurmaId());

        List<Long> matricula = new ArrayList<>();

        entrada.getPessoas().forEach(v -> matricula.add(v.getMatricula()));


        List<Pessoa> pessoas = validaPessoa(matricula);

        cadastraAlunos(pessoas, turma.getId());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Cadastro realizado com sucesso!")
                .status(HttpStatus.CREATED)
                .data("Cadastro realizado com sucesso!")
                .build());
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
        Turma turmaAntiga = buscaTurma(turma.getId());
        Turma turmaAtualizada = turmaRepository.save(Turma.builder()
                .id(turmaAntiga.getId())
                .numero(turma.getNumero())
                .ano(turma.getAno())
                .build());

        return ResponseEntity.ok().body(turmaAtualizada);
    }

    public ResponseEntity buscaPorAno(int ano) {
        return ResponseEntity.ok().body(turmaRepository.findByAno(ano));
    }

    private List<Pessoa> validaPessoa(List<Long> matricula) {
        List<Pessoa> pessoas = new ArrayList<>();

        matricula.forEach(valor -> {
            Pessoa pessoa = validacoesService.buscaPessoa(valor, "Aluno");

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

    private Turma buscaTurma(Long id){
        return turmaRepository.findById(id).orElseThrow(() -> new TurmaNotFoundException(id));
    }

    public ResponseEntity buscaAlunoPorNumero(int numero) {
        Turma turma = validacoesService.buscaTurmaPorNumero(numero);
        List<Long> matriculas = turmaRepository.buscaAlunosPorTurma(turma.getId());

        List<Pessoa> alunos = validaPessoa(matriculas);

        return  ResponseEntity.ok().body(alunos);
    }
}

