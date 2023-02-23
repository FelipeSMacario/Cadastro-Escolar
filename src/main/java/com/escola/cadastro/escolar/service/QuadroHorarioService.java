package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EntradaQuadroAtualizarDTO;
import com.escola.cadastro.escolar.dto.EntradaQuadroHorarioDTO;
import com.escola.cadastro.escolar.dto.SaidaTurmaHorarioDTO;
import com.escola.cadastro.escolar.exception.QuadroHorarioNotFound;
import com.escola.cadastro.escolar.model.*;
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
import java.util.stream.Collectors;

@Service
public class QuadroHorarioService {
    @Autowired
    QuadroHorarioRepository quadroHorarioRepository;
    @Autowired
    HoraRepository horaRepository;
    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private DiasRepositry diasRepositry;
    @Autowired
    SalaRepository salaRepository;


    public ResponseEntity cadastrarSala(EntradaQuadroHorarioDTO entrada) {

        validacoes(entrada);

        quadroHorarioRepository.cadastrarSala(quadroHorarioRepository.buscaIdMaximo(),
                entrada.getIdTurma(),
                entrada.getIdMateria(),
                entrada.getIdHora(), entrada.getIdDia(), entrada.getIdSala());


        return ResponseEntity.ok().body(entrada);
    }

    public ResponseEntity buscarHorasPorDia(Long dia, Long sala) {
        List<Long> horasCadastradas = quadroHorarioRepository.listarHoras(dia, sala);

        List<Horas> horasTotais = horaRepository.findAll();

        horasCadastradas.forEach(valor ->
            horasTotais.stream().filter(hora -> hora.getId().equals(valor)).findFirst().ifPresent(horasTotais::remove));

        return ResponseEntity.ok().body(horasTotais);
    }

    public ResponseEntity atualizarQuadro(EntradaQuadroAtualizarDTO entrada) {
        validacoes(new EntradaQuadroHorarioDTO(entrada.getIdDia(), entrada.getIdHora(), entrada.getIdTurma(), entrada.getIdMateria(), entrada.getIdSala()));

        QuadroHorario quadroHorario = buscaQuadroHorario(entrada.getIdQuadro());
        quadroHorarioRepository.atualizarQuadro(
                entrada.getIdQuadro(),
                entrada.getIdTurma(),
                entrada.getIdMateria(),
                entrada.getIdHora(),
                entrada.getIdDia(),
                entrada.getIdSala()
        );

        return ResponseEntity.ok().body("QUadro atualizado com sucesso");
    }

    public ResponseEntity deletarQuadro(Long id) {
        QuadroHorario quadroHorario = buscaQuadroHorario(id);
        quadroHorarioRepository.deletarQuadro(quadroHorario.getId());

        return ResponseEntity.ok().body("Quadro deletado com sucesso!");
    }

    public ResponseEntity buscarHorarioPorTurma(Long turma) {
        List<SaidaTurmaHorarioDTO> saida = definaTurmaHorario(turma);
        return ResponseEntity.ok().body(saida);
    }

    public ResponseEntity buscarHorarioPorMatricula(Long matricula) {
        Optional<Turma> turma = turmaRepository.findByAlunosMatricula(matricula);
    return turma
            .map(record -> {
                List<SaidaTurmaHorarioDTO> saida = definaTurmaHorario(turma.get().getId());
               return ResponseEntity.ok().body(saida);
            }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    public ResponseEntity buscaHorarioPorId(Long idHorario){
        return ResponseEntity.ok().body(defineHorario(idHorario));
    }

    private List<SaidaTurmaHorarioDTO> definaTurmaHorario(Long turma) {
        List<SaidaTurmaHorarioDTO> saida = new ArrayList<>();
        List<Object[]> horarios = quadroHorarioRepository.listarTurmas(turma);
        horarios.forEach(h -> {
            Long id = Long.parseLong(h[0] + "");
            Optional<Turma> turma1 = turmaRepository.findById(Long.parseLong(h[1] + ""));
            Optional<Materia> materia = materiaRepository.findById(Long.parseLong(h[2] + ""));
            Optional<Horas> horas = horaRepository.findById(Long.parseLong(h[3] + ""));
            Optional<Dia> dia = diasRepositry.findById(Long.parseLong(h[4] + ""));
            Optional<Sala> sala = salaRepository.findById(Long.parseLong(h[5] + ""));
           saida.add(new SaidaTurmaHorarioDTO(id, turma1.get(), materia.get(), horas.get(), dia.get(), sala.get()));
        });

       saida.sort(Comparator.comparingLong(c -> c.getDia().getId()));
        saida.sort(Comparator.comparingLong(c -> c.getHoras().getId()));

        return saida;
    }

    private SaidaTurmaHorarioDTO defineHorario(Long idHorario){
        List<SaidaTurmaHorarioDTO> saida = new ArrayList<>();
        List<Object[]> horarioObjeto = quadroHorarioRepository.filtraHorarioPorId(idHorario);

        horarioObjeto.forEach(h -> {
            Long id = Long.parseLong(h[0] + "");
            Optional<Turma> turma1 = turmaRepository.findById(Long.parseLong(h[1] + ""));
            Optional<Materia> materia = materiaRepository.findById(Long.parseLong(h[2] + ""));
            Optional<Horas> horas = horaRepository.findById(Long.parseLong(h[3] + ""));
            Optional<Dia> dia = diasRepositry.findById(Long.parseLong(h[4] + ""));
            Optional<Sala> sala = salaRepository.findById(Long.parseLong(h[5] + ""));
            saida.add(new SaidaTurmaHorarioDTO(id, turma1.get(), materia.get(), horas.get(), dia.get(), sala.get()));
        });

        return saida.stream().findFirst().get();
    }


    private void validacoes(EntradaQuadroHorarioDTO entrada) {
        if (quadroHorarioRepository.validaDiasDisponivel(entrada.getIdDia(), entrada.getIdHora(), entrada.getIdSala()) >= 1)
            throw new ServiceException("Hora já cadastrada");

        if (quadroHorarioRepository.validaMateriaDisponivel(entrada.getIdDia(), entrada.getIdHora(), entrada.getIdMateria()) >= 1)
            throw new ServiceException("Hora já matéria já cadastrada para esse horário");
    }

    private QuadroHorario buscaQuadroHorario(Long id){
        return quadroHorarioRepository.findById(id).orElseThrow(() -> new QuadroHorarioNotFound(id));
    }
}


