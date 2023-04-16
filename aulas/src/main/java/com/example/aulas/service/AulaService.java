package com.example.aulas.service;

import com.example.aulas.dto.EntradaQuadroAtualizarDTO;
import com.example.aulas.dto.EntradaQuadroHorarioDTO;
import com.example.aulas.enums.Cargo;
import com.example.aulas.model.*;
import com.example.aulas.repository.AulaRepository;
import com.example.aulas.repository.HoraRepository;
import com.example.aulas.repository.MateriaRepository;
import com.example.aulas.response.DefaultResponse;
import com.example.aulas.response.ResponseHoras;
import com.example.aulas.response.ResponseMaterias;
import com.example.aulas.response.ResponseQuadroHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Service
public class AulaService {

    @Autowired
    AulaRepository quadroHorarioRepository;
    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    ValidacoesService validacoesService;
    @Autowired
    HoraRepository horaRepository;
    public ResponseEntity<DefaultResponse> cadastrarSala(EntradaQuadroHorarioDTO entrada) {
        QuadroHorario quadroHorario = quadroHorarioRepository.save(new QuadroHorario(
                        null,
                        validacoesService.buscaTurma(entrada.getIdTurma()),
                        validacoesService. buscaMateriaPorId(entrada.getIdMateria()),
                        validacoesService.buscaHora(entrada.getIdHora()),
                        validacoesService.buscaDia(entrada.getIdDia())
                )

        );

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .messagem("Aula adastrada com sucesso!")
                .data(quadroHorario)
                .build());
    }

    public ResponseEntity<DefaultResponse> buscarHorasPorDia(Long dia, Long turma) {
        List<QuadroHorario> quadroHorarios = quadroHorarioRepository.findByDiaIdAndTurmaId(dia, turma);
        List<Horas> horas = horaRepository.findAll();

        quadroHorarios.forEach(quad ->
            horas.stream().filter(h -> h.getId().equals(quad.getHoras().getId())).findFirst().ifPresent(horas::remove)
        );

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseHoras(horas))
                .build());
    }

    public ResponseEntity<DefaultResponse> filtrarMaterias(Long dia, Long hora) {
        List<Materia> materias = materiaRepository.findAll();

        List<QuadroHorario> quadroHorarios = quadroHorarioRepository.findByDiaIdAndHorasId(dia, hora);

        quadroHorarios.forEach(quad -> {
            materias.stream().filter(mat -> mat.getId().equals(quad.getMateria().getId())).findFirst().ifPresent(materias::remove);
        });

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseMaterias(materias))
                .build());
    }

    public ResponseEntity<DefaultResponse> atualizarQuadro(EntradaQuadroAtualizarDTO entrada) {
        QuadroHorario quadroAtualizado = quadroHorarioRepository.save(new QuadroHorario(
                validacoesService.buscaQuadroHorario(entrada.getIdQuadro()).getId(),
                validacoesService.buscaTurma(entrada.getIdTurma()),
                validacoesService.buscaMateriaPorId(entrada.getIdMateria()),
                validacoesService.buscaHora(entrada.getIdHora()),
                validacoesService.buscaDia(entrada.getIdDia())
        ));

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .messagem("Aula atualizada com sucesso!")
                .data(quadroAtualizado)
                .build());
    }

    public ResponseEntity<DefaultResponse> deletarQuadro(Long id) {
        QuadroHorario quadroHorario = validacoesService.buscaQuadroHorario(id);
        quadroHorarioRepository.deleteById(quadroHorario.getId());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .messagem("Aula deletada com sucesso!")
                .build());
    }

    public ResponseEntity<DefaultResponse> buscarHorarioPorTurma(Long turma, Pageable pageable) {
       var quadroHorarios = quadroHorarioRepository.findByTurmaNumero(turma, pageable);
        if (quadroHorarios.isEmpty()){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .messagem("Nenhuma turma encontrada")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(LocalDate.now())
                    .build());
        }
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data((Serializable) quadroHorarios)
                .build());
    }

    public ResponseEntity<DefaultResponse> buscarHorarioPorMatricula(Long matricula, Pageable pageable) {

        Pessoa aluno = validacoesService.buscaPessoa(matricula, Cargo.Aluno.toString());
        Long idTurma = validacoesService.buscaTurmaPorMatricula(aluno.getMatricula());
        Turma turma = validacoesService.buscaTurma(idTurma);

        var quadroHorarios = quadroHorarioRepository.findByTurmaId(turma.getId(), pageable);

        if (quadroHorarios.isEmpty()){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .messagem("Nenhuma aula identificada com essa matricula")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(LocalDate.now())
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data((Serializable) quadroHorarios )
                .build());
    }

    public ResponseEntity<DefaultResponse> buscaHorarioPorId(Long idHorario) {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(validacoesService.buscaQuadroHorario(idHorario))
                .build());
    }
}
