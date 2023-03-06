package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.*;
import com.escola.cadastro.escolar.exception.AlunoNoRegisterException;
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
import java.util.Objects;
import java.util.Optional;

@Service
public class TurmaService {
    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    ValidacoesService validacoesService;

    private final String cargo = "Aluno";


    public ResponseEntity<DefaultResponse> cadastrarAlunoTurma(EntradaTurmaAlunoDTO entrada) {
        Turma turma = validacoesService.buscaTurma(entrada.getTurmaId());

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
        Turma turmaAntiga = validacoesService.buscaTurma(turma.getId());
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


    public ResponseEntity<DefaultResponse> listarTurmasAlunosPorId(Long id) {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                 .data(buscaAlunoTurmaPorId(id))
                .status(HttpStatus.OK)
                .build());
    }

    public ResponseEntity listarTurmaPorMatricula(Long matricula) {
        Optional<Pessoa> pessoa = pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo");
        Turma turma = pessoa.get().getTurmas().stream().findFirst().orElse(new Turma());
        return pessoa
                .map(record -> {
                    SaidaAlunoTurmaDTO saida = new SaidaAlunoTurmaDTO(pessoa.get(), turma);
                    return ResponseEntity.ok().body(saida);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<DefaultResponse> removerAlunoTurma(Long matricula, Long id) {
        Turma turma = validacoesService.buscaTurma(id);
        Pessoa aluno = validacoesService.buscaPessoa(matricula, cargo);

        turmaRepository.deletaAluno(aluno.getMatricula(), turma.getId());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Aluno removido da turma!")
                .status(HttpStatus.OK)
                .build());
    }

    public ResponseEntity listarAlunosPorAno(Integer ano) {
        List<Pessoa> pessoas = pessoaRepository.findByCargoAndStatusAndAno(cargo, "Ativo", ano);
        List<Pessoa> pessoasFiltradas = new ArrayList<>();
        pessoas.forEach(p -> {

            if (turmaRepository.validaAluno(p.getMatricula()).get() == 0) pessoasFiltradas.add(p);

        });
        return ResponseEntity.ok().body(pessoasFiltradas);
    }



    public ResponseEntity buscaAlunoPorNumero(int numero) {
        Turma turma = validacoesService.buscaTurmaPorNumero(numero);
        List<Long> matriculas = turmaRepository.buscaAlunosPorTurma(turma.getId());

        List<Pessoa> alunos = validaPessoa(matriculas);

        List<AlunoTurmaDTO> turmaAluno = new ArrayList<>();

        alunos.forEach(v -> turmaAluno.add(new AlunoTurmaDTO(null, v, turma)));

        return  ResponseEntity.ok().body(turmaAluno);
    }

    public ResponseEntity<DefaultResponse> buscaTurmaPorMatricula(Long matricula) {
        Pessoa aluno = validacoesService.buscaPessoa(matricula, cargo);
        Long turmaId = validacoesService.buscaTurmaPorMatricula(aluno.getMatricula());

        Turma turma = validacoesService.buscaTurma(turmaId);

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem(null)
                .status(HttpStatus.CREATED)
                .data(new AlunoTurmaDTO(null, aluno, turma))
                .build());
    }

    public ResponseEntity listarAlunosTurma() {
        return  ResponseEntity.ok().body(listarAlunoTurma());
    }

    private List<AlunoTurmaDTO> listarAlunoTurma(){
        List<Object[]> listaAlunoTurma = turmaRepository.buscaAlunoTurma();
        List<AlunoTurmaDTO> turmaAluno = new ArrayList<>();

        listaAlunoTurma.forEach(v -> {
            turmaAluno.add(new AlunoTurmaDTO(null, validacoesService.buscaPessoa(Long.parseLong(v[2] + ""), cargo), validacoesService.buscaTurma(Long.parseLong(v[1] + ""))));
        });

            return turmaAluno;
    }

    public ResponseEntity listarturmaAlunoPorNome(String nome) {
        List<Pessoa> alunos = pessoaRepository.findByNomeAndCargoAndStatus(nome, cargo, "Ativo");
        List<AlunoTurmaDTO> turmaAluno = new ArrayList<>();

        alunos.forEach(v -> {
            Turma turma = validacoesService.buscaTurma(validacoesService.buscaTurmaPorMatricula(v.getMatricula()));
            turmaAluno.add(new AlunoTurmaDTO(null, v, turma));
        });

        return ResponseEntity.ok().body(turmaAluno);
    }

    public ResponseEntity<DefaultResponse> buscaTurmaAluno(Long matricula, Long turma) {

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(buscaAlunoTurma(matricula,turma))
                .build());
    }

    AlunoTurmaDTO buscaAlunoTurma(Long matricula, Long turma){
        AlunoTurmaDTO alunoTurmaDTO = new AlunoTurmaDTO();
        List<Object[]> lista = turmaRepository.definaAlunoTurma(matricula, turma);

        lista.forEach(v -> {
            alunoTurmaDTO.setId(Long.parseLong(v[0] + ""));
            alunoTurmaDTO.setTurma(validacoesService.buscaTurma(Long.parseLong(v[1] + "")));
            alunoTurmaDTO.setAluno(validacoesService.buscaPessoa(Long.parseLong(v[2] + ""), cargo));
        });
        return alunoTurmaDTO;
    }

    AlunoTurmaDTO buscaAlunoTurmaPorId(Long id){
        AlunoTurmaDTO alunoTurmaDTO = new AlunoTurmaDTO();
        List<Object[]> lista = turmaRepository.definaAlunoTurmaPorId(id);

        lista.forEach(v -> {
            alunoTurmaDTO.setId(Long.parseLong(v[0] + ""));
            alunoTurmaDTO.setTurma(validacoesService.buscaTurma(Long.parseLong(v[1] + "")));
            alunoTurmaDTO.setAluno(validacoesService.buscaPessoa(Long.parseLong(v[2] + ""), cargo));
        });
        return alunoTurmaDTO;
    }

    public ResponseEntity<DefaultResponse> atualizarAlunoTurma(AlunoTurmaDTO entradaTurmaAlunoDTO) {
        turmaRepository.atualizaAlunoTurma(entradaTurmaAlunoDTO.getId(), entradaTurmaAlunoDTO.getTurma().getId());
         return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Atualização realizada com sucesso!")
                .status(HttpStatus.OK)
                .build());
    }
}

