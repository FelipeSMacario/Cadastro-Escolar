package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.QuadroHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface QuadroHorarioRepository extends JpaRepository<QuadroHorario, Long> {

    @Query(value = "SELECT count(*) FROM sistemaescolar.quadro_horario WHERE dia_id = :diaID AND hora_id = :horaId AND sala_id = :salaId", nativeQuery = true)
    int validaDiasDisponivel(@Param("diaID") Long diaId, @Param("horaId") Long horaId, @Param("salaId") Long salaId);

    @Query(value = "SELECT count(*) FROM sistemaescolar.quadro_horario WHERE dia_id = :diaID AND hora_id = :horaId AND materia_id = :materiaId", nativeQuery = true)
    int validaMateriaDisponivel(@Param("diaID") Long diaId, @Param("horaId") Long horaId, @Param("materiaId") Long materiaId);

    @Query(value = "SELECT ifnull(max(id), 0) + 1 FROM sistemaescolar.quadro_horario", nativeQuery = true)
    Long buscaIdMaximo();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sistemaescolar.quadro_horario VALUES (:idQuadro, :idTurma, :idMateria, :horaId, :diaID, :salaId)", nativeQuery = true)
    void cadastrarSala(@Param("idQuadro") Long idQuadro, @Param("idTurma") Long idTurma, @Param("idMateria") Long idMateria,  @Param("horaId") Long horaId, @Param("diaID") Long diaId, @Param("salaId") Long salaId);

    @Query(value = "SELECT hora_id FROM sistemaescolar.quadro_horario WHERE dia_id = :idDia AND sala_id = :salaId", nativeQuery = true)
    List<Long> listarHoras(@Param("idDia") Long idDia, @Param("salaId") Long salaId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE sistemaescolar.quadro_horario SET turma_id = :idTurma, materia_id = :idMateria, hora_id = :horaId, dia_id = :diaID, sala_id = :salaId WHERE id = :idQuadro", nativeQuery = true)
    void atualizarQuadro(@Param("idQuadro") Long idQuadro,
                       @Param("idTurma") Long idTurma,
                       @Param("idMateria") Long idMateria,
                       @Param("horaId") Long horaId,
                       @Param("diaID") Long diaId,
                       @Param("salaId") Long salaId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM  sistemaescolar.quadro_horario WHERE id = :quadroId", nativeQuery = true)
    void deletarQuadro(@Param("quadroId") Long quadroId);
}
