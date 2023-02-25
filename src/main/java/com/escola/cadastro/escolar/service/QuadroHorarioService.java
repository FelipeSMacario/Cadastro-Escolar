package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EntradaQuadroAtualizarDTO;
import com.escola.cadastro.escolar.dto.EntradaQuadroHorarioDTO;
import com.escola.cadastro.escolar.dto.SaidaTurmaHorarioDTO;
import com.escola.cadastro.escolar.exception.*;
import com.escola.cadastro.escolar.model.*;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.repository.*;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class QuadroHorarioService {
    @Autowired
    QuadroHorarioRepository quadroHorarioRepository;
    @Autowired
    HoraRepository horaRepository;
    @Autowired
    TurmaRepository turmaRepository;
    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    DiasRepositry diasRepositry;
    @Autowired
    PessoaRepository pessoaRepository;


    public ResponseEntity<DefaultResponse> cadastrarSala(EntradaQuadroHorarioDTO entrada) {
        QuadroHorario quadroHorario = quadroHorarioRepository.save(new QuadroHorario(
                        null,
                        buscaTurma(entrada.getIdTurma()),
                        buscaMateriaPorId(entrada.getIdMateria()),
                        buscaHora(entrada.getIdHora()),
                        buscaDia(entrada.getIdDia())
                )

        );

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .messagem("Aula adastrada com sucesso!")
                .data(quadroHorario)
                .build());
    }

    public ResponseEntity buscarHorasPorDia(Long dia, Long turma) {
        List<QuadroHorario> quadroHorarios = quadroHorarioRepository.findByDiaIdAndTurmaId(dia, turma);
        List<Horas> horas = horaRepository.findAll();

        quadroHorarios.forEach(quad -> {
            horas.stream().filter(h -> h.getId().equals(quad.getHoras().getId())).findFirst().ifPresent(horas::remove);
        });

        return ResponseEntity.ok().body(horas);
    }

    public ResponseEntity filtrarMaterias(Long dia, Long hora) {
        List<Materia> materias = materiaRepository.findAll();

        List<QuadroHorario> quadroHorarios = quadroHorarioRepository.findByDiaIdAndHorasId(dia, hora);

        quadroHorarios.forEach(quad -> {
            materias.stream().filter(mat -> mat.getId().equals(quad.getMateria().getId())).findFirst().ifPresent(materias::remove);
        });

        return ResponseEntity.ok().body(materias);
    }

    public ResponseEntity<DefaultResponse> atualizarQuadro(EntradaQuadroAtualizarDTO entrada) {
        QuadroHorario quadroAtualizado = quadroHorarioRepository.save(new QuadroHorario(
                buscaQuadroHorario(entrada.getIdQuadro()).getId(),
                buscaTurma(entrada.getIdTurma()),
                buscaMateriaPorId(entrada.getIdMateria()),
                buscaHora(entrada.getIdHora()),
                buscaDia(entrada.getIdDia())
        ));

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .messagem("Aula atualizada com sucesso!")
                .data(quadroAtualizado)
                .build());
    }

    public ResponseEntity<DefaultResponse> deletarQuadro(Long id) {
        QuadroHorario quadroHorario = buscaQuadroHorario(id);
        quadroHorarioRepository.deleteById(quadroHorario.getId());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .messagem("Aula deletada com sucesso!")
                .build());
    }

    public ResponseEntity buscarHorarioPorTurma(Long turma) {
        List<QuadroHorario> quadroHorarios = quadroHorarioRepository.findByTurmaId(turma);
        return ResponseEntity.ok().body(quadroHorarios);
    }

    public ResponseEntity buscarHorarioPorMatricula(Long matricula) {
        Pessoa aluno = buscaPessoa(matricula, "Aluno");
        Optional<Turma> turma = turmaRepository.findByAlunosMatricula(aluno.getMatricula());

        List<QuadroHorario> quadroHorarios = quadroHorarioRepository.findByTurmaId(turma.get().getId());
        return ResponseEntity.ok().body(quadroHorarios);

    }

    public ResponseEntity<DefaultResponse> buscaHorarioPorId(Long idHorario) {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(buscaQuadroHorario(idHorario))
                .build());
    }

    private QuadroHorario buscaQuadroHorario(Long id) {
        return quadroHorarioRepository.findById(id).orElseThrow(() -> new QuadroHorarioNotFound(id));
    }

    private Turma buscaTurma(Long id) {
        return turmaRepository.findById(id).orElseThrow(() -> new TurmaNotFoundException(id));
    }

    private Materia buscaMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElseThrow(() -> new MateriaNotFoundException("", id));
    }

    private Dia buscaDia(Long id) {
        return diasRepositry.findById(id).orElseThrow(() -> new DiaNotFoundException(id));
    }

    private Horas buscaHora(Long id) {
        return horaRepository.findById(id).orElseThrow(() -> new HoraNotFoundException(id));
    }
    private Pessoa buscaPessoa(Long matricula, String cargo){
        return pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo").orElseThrow(() -> new UserNotFoundException(matricula));
    }
}


