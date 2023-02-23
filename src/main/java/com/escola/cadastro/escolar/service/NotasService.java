package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.NotasDTO;
import com.escola.cadastro.escolar.dto.NotasPessoaDTO;
import com.escola.cadastro.escolar.dto.NotasSaidaDTO;
import com.escola.cadastro.escolar.dto.NotasTrimestreDTO;
import com.escola.cadastro.escolar.exception.*;
import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.Notas;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Turma;
import com.escola.cadastro.escolar.repository.MateriaRepository;
import com.escola.cadastro.escolar.repository.NotasRepository;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import com.escola.cadastro.escolar.repository.TurmaRepository;
import org.aspectj.weaver.ast.Not;
import org.hibernate.service.spi.ServiceException;
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
    PessoaRepository pessoaRepository;
    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    TurmaRepository turmaRepository;

    public ResponseEntity cadastrarNotas(NotasDTO notasDTO) {
        Pessoa professor = buscaPessoa(notasDTO.getMatriculaProfessor(), "Professor");
        Materia materia = buscaMateriaPorNome(notasDTO.getMateria());
        Turma turma = buscaTurma(notasDTO.getTurmaId());

        notasDTO.getMatriculasNotas().forEach(valor -> {
            Pessoa aluno = buscaPessoa(valor.getMatriculaAluno(), "Aluno");
            validaTrimeste(professor.getMatricula(), aluno.getMatricula(), materia.getId(), buscaTrimeste(), notasDTO.getTurmaId());
            Notas motas = Notas.builder()
                    .nota(valor.getNotas())
                    .professor(professor)
                    .turma(turma)
                    .aluno(aluno)
                    .materia(materia)
                    .dataInclusao(LocalDate.now())
                    .trimestre(buscaTrimeste()).build();
            notasRepository.save(motas);
        });

        return ResponseEntity.ok().body(notasDTO);
    }

    public ResponseEntity alterarNotas(NotasTrimestreDTO notasDTO) {
        Notas notas = buscaNotas(notasDTO.getNotaId());
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

        return ResponseEntity.ok().body(notaAtualizada);
    }

    public ResponseEntity deletarNotas(NotasTrimestreDTO notasDTO) {
        Notas notas = buscaNotas(notasDTO.getNotaId());
        notasRepository.deleteById(notas.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Nota deletada com sucesso");
    }

    public ResponseEntity listarNotas(NotasPessoaDTO notasPessoaDTO) {
        Optional<Pessoa> pessoa = pessoaRepository.findByMatriculaAndStatus(notasPessoaDTO.getMatriculaPessoa(), "Ativo");
        return pessoa
                .map(record -> {
                    List<Notas> notas = record.getCargo().equals("Professor")
                            ? notasRepository.buscaNotasProfessor(notasPessoaDTO.getMatriculaPessoa(), notasPessoaDTO.getTrimeste())
                            : notasRepository.buscaNotasAluno(notasPessoaDTO.getMatriculaPessoa(), notasPessoaDTO.getTrimeste());

                    List<NotasSaidaDTO> notasSaidaDTOS = new ArrayList<>();
                    notas.forEach(v -> notasSaidaDTOS.add(
                            NotasSaidaDTO.builder()
                                    .nome(pessoa.get().getNome())
                                    .sobreNome(pessoa.get().getSobreNome())
                                    .materia(v.getMateria().getNome())
                                    .nota(v.getNota()).build()
                    ));
                    return ResponseEntity.ok().body(notasSaidaDTOS);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity buscaNotaPorId(Long id) {
        return ResponseEntity.ok().body( buscaNotas(id));
    }

    public ResponseEntity buscaNotasPorTurmaAMateria(Long idTurma, Long idMateria) {
        Turma turma = buscaTurma(idTurma);
        Materia materia = buscaMateriaPorId(idMateria);

        return ResponseEntity.ok().body(notasRepository.findByTurmaIdAndMateriaId(turma.getId(), materia.getId()));
    }

    public ResponseEntity buscaPorMatricula(Long matricula) {
        Pessoa aluno = buscaPessoa(matricula, "Aluno");
        return ResponseEntity.ok().body(notasRepository.findByAlunoMatricula(aluno.getMatricula()));
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

    private Turma buscaTurma(Long turmaId) {
        return turmaRepository.findById(turmaId).orElseThrow(() -> new TurmaNotFound(turmaId));
    }
    private Materia buscaMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElseThrow(() -> new MateriaNotFoundException("",id));
    }

    private Pessoa buscaPessoa(Long matricula, String cargo){
        return pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo").orElseThrow(() -> new UserNotFoundException(matricula));
    }
    private Materia buscaMateriaPorNome(String nome) {
        return materiaRepository.findByNome(nome).orElseThrow(() -> new MateriaNotFoundException(nome));
    }
    private Notas buscaNotas(Long id){
        return notasRepository.findById(id).orElseThrow(() -> new NotasNotFoundException(id));
    }
}
