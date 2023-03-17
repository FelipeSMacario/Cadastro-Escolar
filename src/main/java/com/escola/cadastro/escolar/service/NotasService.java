package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EmailNotasDTO;
import com.escola.cadastro.escolar.dto.NotasDTO;
import com.escola.cadastro.escolar.dto.NotasTrimestreDTO;
import com.escola.cadastro.escolar.enums.Fila;
import com.escola.cadastro.escolar.exception.*;
import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.Notas;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Turma;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.model.response.ResponseFiltroPessoaNome;
import com.escola.cadastro.escolar.model.response.ResponseNotas;
import com.escola.cadastro.escolar.repository.NotasRepository;

import com.escola.cadastro.escolar.repository.TurmaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotasService {
    @Autowired
    NotasRepository notasRepository;
    @Autowired
    ValidacoesService validacoesService;

    @Autowired
    TurmaAlunoRepository turmaAlunoRepository;
    @Autowired
    RabbitmqService rabbitmqService;



    public ResponseEntity<DefaultResponse> cadastrarNotas(NotasDTO notasDTO) {
        Pessoa professor = validacoesService.buscaPessoa(notasDTO.getMatriculaProfessor(), "Professor");
        Materia materia = validacoesService.buscaMateriaPorNome(notasDTO.getMateria());
        Turma turma = validacoesService.buscaTurma(notasDTO.getTurmaId());

        notasDTO.getMatriculasNotas().forEach(valor -> {
            Pessoa aluno = validacoesService.buscaPessoa(valor.getMatriculaAluno(), "Aluno");
            validaTrimeste(professor.getMatricula(), aluno.getMatricula(), materia.getId(), buscaTrimeste(), notasDTO.getTurmaId());
            Notas notas = Notas.builder()
                    .nota(valor.getNotas())
                    .professor(professor)
                    .turma(turma)
                    .aluno(aluno)
                    .materia(materia)
                    .dataInclusao(LocalDate.now())
                    .trimestre(buscaTrimeste()).build();
            notasRepository.save(notas);

            EmailNotasDTO emailNotasDTO = new EmailNotasDTO(aluno.getEmail(), aluno.getNome(), valor.getNotas(), materia.getNome());
            rabbitmqService.enviaMensagem(Fila.NOTAS.toString(), emailNotasDTO);

        });

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .messagem("Notas cadastradas com sucesso!")
                .build());
    }

    public ResponseEntity<DefaultResponse> alterarNotas(NotasTrimestreDTO notasDTO) {
        Notas notas = validacoesService.buscaNotas(notasDTO.getNotaId());
        Notas notaAtualizada = notasRepository.save(Notas.builder()
                .id(notas.getId())
                .professor(notas.getProfessor())
                .aluno(notas.getAluno())
                .materia(notas.getMateria())
                .trimestre(notas.getTrimestre())
                .turma(notas.getTurma())
                .dataInclusao(notas.getDataInclusao())
                .nota(notasDTO.getNota())
                .build());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(notaAtualizada)
                .build());
    }


    public ResponseEntity<DefaultResponse> buscaNotaPorId(Long id) {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(validacoesService.buscaNotas(id))
                .build());
    }

    public ResponseEntity<DefaultResponse> buscaNotasPorTurmaAMateria(Long idTurma, Long idMateria) {
        Turma turma = validacoesService.buscaTurma(idTurma);
        Materia materia = validacoesService.buscaMateriaPorId(idMateria);

        List<Notas> notas = notasRepository.findByTurmaIdAndMateriaId(turma.getId(), materia.getId());

        if (notas.isEmpty()){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .messagem("Nenhuma nota identificada!")
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .messagem("Aula adastrada com sucesso!")
                .data(new ResponseNotas(notas))
                .build());
    }

    public ResponseEntity<DefaultResponse> buscaPorMatricula(Long matricula) {
        Pessoa aluno = validacoesService.buscaPessoa(matricula, "Aluno");

        List<Notas> notas = notasRepository.findByAlunoMatricula(aluno.getMatricula());

        if (notas.isEmpty()){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .messagem("Nenhuma nota identificada!")
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseNotas(notas))
                .build());
    }

    private Integer buscaTrimeste() {
        LocalDate datas = LocalDate.now();
        if (datas.isAfter(LocalDate.of(LocalDate.now().getYear(), 1, 1)) && datas.isBefore(LocalDate.of(LocalDate.now().getYear(), 3, 31)))
            return 1;

        if (datas.isAfter(LocalDate.of(LocalDate.now().getYear(), 4, 1)) && datas.isBefore(LocalDate.of(LocalDate.now().getYear(), 6, 30)))
            return 2;

        if (datas.isAfter(LocalDate.of(LocalDate.now().getYear(), 7, 1)) && datas.isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 30)))
            return 3;

        if (datas.isAfter(LocalDate.of(LocalDate.now().getYear(), 10, 1)) && datas.isBefore(LocalDate.of(LocalDate.now().getYear(), 12, 31)))
            return 4;

        return 0;

    }

    private void validaTrimeste(Long idProfessor, Long idAluno, Long idMateria, Integer semestre, Long idTurma) {
        Optional<Notas> notas = notasRepository.buscaNotas(idProfessor, idAluno, idMateria, semestre, idTurma);
        if (notas.isPresent())
            throw new NotasAlreadyExistsException(notas.get().getId());
    }

    public ResponseEntity<DefaultResponse> buscarAlunosPorTurma(Long idTurma, Long idMateria) {
        List<Long> matricula = turmaAlunoRepository.buscaAlunosPorTurma(idTurma);
        List<Pessoa> alunos = validacoesService.validaPessoa(matricula);

        List<Pessoa> alunosFiltrados = new ArrayList<>();

        Turma turma = validacoesService.buscaTurma(idTurma);
        Materia materia = validacoesService.buscaMateriaPorId(idMateria);

        List<Notas> notas = notasRepository.findByTurmaIdAndMateriaId(turma.getId(), materia.getId());

        alunos.forEach(a -> {
            if(notas.stream().noneMatch(n -> n.getAluno().getMatricula().equals(a.getMatricula())))
                alunosFiltrados.add(a);
        });

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseFiltroPessoaNome(alunosFiltrados))
                .build());
    }
}
