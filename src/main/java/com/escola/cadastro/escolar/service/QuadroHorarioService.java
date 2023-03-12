package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EntradaQuadroAtualizarDTO;
import com.escola.cadastro.escolar.dto.EntradaQuadroHorarioDTO;
import com.escola.cadastro.escolar.enums.Cargo;
import com.escola.cadastro.escolar.model.*;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.model.response.ResponseHoras;
import com.escola.cadastro.escolar.model.response.ResponseMaterias;
import com.escola.cadastro.escolar.model.response.ResponseQuadroHorario;
import com.escola.cadastro.escolar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuadroHorarioService {
    @Autowired
    QuadroHorarioRepository quadroHorarioRepository;
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

        quadroHorarios.forEach(quad -> {
            horas.stream().filter(h -> h.getId().equals(quad.getHoras().getId())).findFirst().ifPresent(horas::remove);
        });

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

    public ResponseEntity<DefaultResponse> buscarHorarioPorTurma(Long turma) {
        List<QuadroHorario> quadroHorarios = quadroHorarioRepository.findByTurmaId(turma);
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
                .data(new ResponseQuadroHorario(quadroHorarios))
                .build());
    }

    public ResponseEntity<DefaultResponse> buscarHorarioPorMatricula(Long matricula) {

        Pessoa aluno = validacoesService.buscaPessoa(matricula, Cargo.Aluno.toString());
        Long idTurma = validacoesService.buscaTurmaPorMatricula(aluno.getMatricula());
        Turma turma = validacoesService.buscaTurma(idTurma);

        List<QuadroHorario> quadroHorarios = quadroHorarioRepository.findByTurmaId(turma.getId());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseQuadroHorario(quadroHorarios))
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


