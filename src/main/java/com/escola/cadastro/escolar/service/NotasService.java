package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.NotasDTO;
import com.escola.cadastro.escolar.dto.NotasPessoaDTO;
import com.escola.cadastro.escolar.dto.NotasSaidaDTO;
import com.escola.cadastro.escolar.dto.NotasTrimestreDTO;
import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.Notas;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.repository.MateriaRepository;
import com.escola.cadastro.escolar.repository.NotasRepository;
import com.escola.cadastro.escolar.repository.PessoaRepository;
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

    public ResponseEntity cadastrarNotas(NotasDTO notasDTO) {
        Optional<Pessoa> professor = buscaPessoa(notasDTO.getMatriculaProfessor(), "Professor");

        Optional<Pessoa> aluno = buscaPessoa(notasDTO.getMatriculaAluno(), "Aluno");

        Optional<Materia> materia = buscaMateria(notasDTO.getMateria());

        if (validaTrimeste(professor.get().getMatricula(), aluno.get().getMatricula(), materia.get().getId(), buscaTrimeste(LocalDate.now())))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe notas cadastradas nesse trimeste para esse aluno nessa materia");

        notasRepository.save(
                Notas.builder()
                        .professor(professor.get())
                        .aluno(aluno.get())
                        .materia(materia.get())
                        .nota(notasDTO.getNota())
                        .dataInclusao(LocalDate.now())
                        .trimeste(buscaTrimeste(LocalDate.now()))
                        .build()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("notas cadastradas com sucesso");
    }

    public ResponseEntity alterarNotas(NotasTrimestreDTO notasDTO) {
        Optional<Notas> notas = buscaNotas(notasDTO);

        return notas
                .map(record -> {
                    Notas notaAtualizada = notasRepository.save(Notas.builder()
                            .id(notas.get().getId())
                            .professor(notas.get().getProfessor())
                            .aluno(notas.get().getAluno())
                            .materia(notas.get().getMateria())
                            .trimeste(notas.get().getTrimeste())
                            .dataInclusao(notas.get().getDataInclusao())
                            .nota(notasDTO.getNota())
                            .build());
                    return ResponseEntity.ok().body(notaAtualizada);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity deletarNotas(NotasTrimestreDTO notasDTO) {
        Optional<Notas> notas = buscaNotas(notasDTO);

        return notas
                .map(record -> {
                    notasRepository.deleteById(notas.get().getId());
                    return ResponseEntity.status(HttpStatus.OK).body("Nota deletada com sucesso");
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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

    private Integer buscaTrimeste(LocalDate datas) {
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

    private boolean validaTrimeste(Long idProfessor, Long idAluno, Long idMateria, Integer semestre) {
        Optional<Notas> notas = notasRepository.buscaNotas(idProfessor, idAluno, idMateria, semestre);
        return notas.isPresent();
    }

    private Optional<Pessoa> buscaPessoa(Long matricula, String cargo) {
        return Optional.ofNullable(pessoaRepository.findByMatriculaAndCargo(matricula, cargo)
                .orElseThrow(() -> new ServiceException("Nenhum " + cargo + " identificado com essas carecterísticas")));
    }

    private Optional<Materia> buscaMateria(String nome) {
        return Optional.ofNullable(materiaRepository.findByNome(nome))
                .orElseThrow(() -> new ServiceException("Nenhuma materia identificado com essas carecterísticas"));
    }

    private Optional<Notas> buscaNotas(NotasTrimestreDTO notasDTO) {
        Optional<Pessoa> professor = buscaPessoa(notasDTO.getMatriculaProfessor(), "Professor");

        Optional<Pessoa> aluno = buscaPessoa(notasDTO.getMatriculaAluno(), "Aluno");

        Optional<Materia> materia = buscaMateria(notasDTO.getMateria());

        return Optional.ofNullable(notasRepository.buscaNotas(professor.get().getMatricula(), aluno.get().getMatricula(), materia.get().getId(), notasDTO.getTrimeste()))
                .orElseThrow(() -> new ServiceException("Nenhuma nota identificada"));
    }


}
