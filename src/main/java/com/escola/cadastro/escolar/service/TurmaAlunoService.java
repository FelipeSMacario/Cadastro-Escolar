package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.AlunoTurmaDTO;
import com.escola.cadastro.escolar.dto.EntradaTurmaAlunoDTO;
import com.escola.cadastro.escolar.dto.SaidaAlunoTurmaDTO;
import com.escola.cadastro.escolar.enums.Cargo;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Turma;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.model.response.ResponseFiltroPessoaNome;
import com.escola.cadastro.escolar.model.response.TurmaAlunoResponse;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import com.escola.cadastro.escolar.repository.TurmaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurmaAlunoService {
    @Autowired
    ValidacoesService validacoesService;

    @Autowired
    TurmaAlunoRepository turmaAlunoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

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
                .build());
    }

    public ResponseEntity<DefaultResponse> listarAlunosTurma() {

        List<AlunoTurmaDTO> lista = new ArrayList<>();

        try {
            lista = listarAlunoTurma();
        }catch (Exception e){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .timestamp(LocalDate.now())
                    .messagem(e.getMessage())
                    .status(HttpStatus.OK)
                    .build());
        }


        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Cadastro realizado com sucesso!")
                .status(HttpStatus.OK)
                .data(new TurmaAlunoResponse(lista))
                .build());
    }

    public ResponseEntity<DefaultResponse> buscaAlunoPorNumero(int numero) {
        Turma turma = validacoesService.buscaTurmaPorNumero(numero);
        List<Long> matriculas = turmaAlunoRepository.buscaAlunosPorTurma(turma.getId());

        List<Pessoa> alunos = validaPessoa(matriculas);

        List<AlunoTurmaDTO> turmaAluno = new ArrayList<>();

        alunos.forEach(v -> turmaAluno.add(new AlunoTurmaDTO(null, v, turma)));

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Cadastro realizado com sucesso!")
                .status(HttpStatus.OK)
                .data(new TurmaAlunoResponse(turmaAluno))
                .build());
    }

    public ResponseEntity<DefaultResponse> listarturmaAlunoPorNome(String nome) {
        List<Pessoa> alunos = pessoaRepository.findByNomeAndCargoAndStatus(nome, Cargo.Aluno.toString(), "Ativo");

        if (alunos.isEmpty()){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .timestamp(LocalDate.now())
                    .messagem("Nenhum aluno identificado com esse nome")
                    .status(HttpStatus.OK)
                    .build());
        }

        List<AlunoTurmaDTO> turmaAluno = new ArrayList<>();

        alunos.forEach(v -> {
            Turma turma = validacoesService.buscaTurma(validacoesService.buscaTurmaPorMatricula(v.getMatricula()));
            turmaAluno.add(new AlunoTurmaDTO(null, v, turma));
        });

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new TurmaAlunoResponse(turmaAluno))
                .build());

    }

    public ResponseEntity<DefaultResponse> listarTurmaPorMatricula(Long matricula) {
        Pessoa pessoa = validacoesService.buscaPessoa(matricula, Cargo.Aluno.toString());
        Turma turma = validacoesService.buscaTurma(validacoesService.buscaTurmaPorMatricula(matricula));

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new SaidaAlunoTurmaDTO(pessoa, turma))
                .build());

    }

    public ResponseEntity<DefaultResponse> listarAlunosPorAno(Integer ano) {
        List<Pessoa> pessoas = pessoaRepository.findByCargoAndStatusAndAno(Cargo.Aluno.toString(), "Ativo", ano);
        List<Pessoa> pessoasFiltradas = new ArrayList<>();
        pessoas.forEach(p -> {

            if (turmaAlunoRepository.validaAluno(p.getMatricula()).get() == 0) pessoasFiltradas.add(p);

        });

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseFiltroPessoaNome(pessoasFiltradas))
                .build());
    }

    public ResponseEntity<DefaultResponse> buscaTurmaPorMatricula(Long matricula) {
        Pessoa aluno = validacoesService.buscaPessoa(matricula, Cargo.Aluno.toString());
        Long turmaId = validacoesService.buscaTurmaPorMatricula(aluno.getMatricula());

        Turma turma = validacoesService.buscaTurma(turmaId);

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .data(new AlunoTurmaDTO(null, aluno, turma))
                .build());
    }

    public ResponseEntity<DefaultResponse> buscaTurmaAluno(Long matricula, Long turma) {

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(buscaAlunoTurma(matricula,turma))
                .build());
    }

    public ResponseEntity<DefaultResponse> removerAlunoTurma(Long matricula, Long id) {
        Turma turma = validacoesService.buscaTurma(id);
        Pessoa aluno = validacoesService.buscaPessoa(matricula, Cargo.Aluno.toString());

        turmaAlunoRepository.deletaAluno(aluno.getMatricula(), turma.getId());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Aluno removido da turma!")
                .status(HttpStatus.OK)
                .build());
    }

    public ResponseEntity<DefaultResponse> atualizarAlunoTurma(AlunoTurmaDTO entradaTurmaAlunoDTO) {
        turmaAlunoRepository.atualizaAlunoTurma(entradaTurmaAlunoDTO.getId(), entradaTurmaAlunoDTO.getTurma().getId());
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Atuali zação realizada com sucesso!")
                .status(HttpStatus.OK)
                .build());
    }

    public ResponseEntity buscaAlunoPorTurma(Long id) {
        List<Pessoa> pessoas = new ArrayList<>();

        try {
            pessoas = validaPessoa(turmaAlunoRepository.buscaAlunosPorTurma(id));
        }catch (Exception e){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .messagem(e.getMessage())
                    .status(HttpStatus.OK)
                    .timestamp(LocalDate.now())
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Cadastro realizado com sucesso!")
                .status(HttpStatus.OK)
                .data(new ResponseFiltroPessoaNome(pessoas))
                .build());
    }
    private void cadastraAlunos(List<Pessoa> pessoas, Long turmaId) {
        pessoas.forEach(p -> {
            turmaAlunoRepository.cadastrarTurmaAluno(turmaAlunoRepository.buscaIdMaximo(), turmaId, p.getMatricula());
        });
    }

    private List<Pessoa> validaPessoa(List<Long> matricula) {
        List<Pessoa> pessoas = new ArrayList<>();

        matricula.forEach(valor -> {
            Pessoa pessoa = validacoesService.buscaPessoa(valor, Cargo.Aluno.toString());

            pessoas.add(pessoa);
        });


        return pessoas;
    }

    private List<AlunoTurmaDTO> listarAlunoTurma(){
        List<Object[]> listaAlunoTurma = turmaAlunoRepository.buscaAlunoTurma();
        List<AlunoTurmaDTO> turmaAluno = new ArrayList<>();

        listaAlunoTurma.forEach(v -> {
            turmaAluno.add(new AlunoTurmaDTO(null, validacoesService.buscaPessoa(Long.parseLong(v[2] + ""), Cargo.Aluno.toString()), validacoesService.buscaTurma(Long.parseLong(v[1] + ""))));
        });

        return turmaAluno;
    }

    AlunoTurmaDTO buscaAlunoTurma(Long matricula, Long turma){
        AlunoTurmaDTO alunoTurmaDTO = new AlunoTurmaDTO();
        List<Object[]> lista = turmaAlunoRepository.definaAlunoTurma(matricula, turma);

        lista.forEach(v -> {
            alunoTurmaDTO.setId(Long.parseLong(v[0] + ""));
            alunoTurmaDTO.setTurma(validacoesService.buscaTurma(Long.parseLong(v[1] + "")));
            alunoTurmaDTO.setAluno(validacoesService.buscaPessoa(Long.parseLong(v[2] + ""), Cargo.Aluno.toString()));
        });
        return alunoTurmaDTO;
    }

}
