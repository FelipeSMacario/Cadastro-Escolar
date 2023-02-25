package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.QuadroHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public interface QuadroHorarioRepository extends JpaRepository<QuadroHorario, Long> {
    List<QuadroHorario> findByDiaIdAndTurmaId(Long dia, Long hora);

    List<QuadroHorario> findByDiaIdAndHorasId(Long dia, Long hora);

    List<QuadroHorario> findByTurmaId(Long turma);
}
