package com.example.Turma.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Turma.model.QuadroHorario;

import java.util.List;

@Repository
public interface QuadroHorarioRepository extends JpaRepository<QuadroHorario, Long> {
    List<QuadroHorario> findByDiaIdAndTurmaId(Long dia, Long hora);

    List<QuadroHorario> findByDiaIdAndHorasId(Long dia, Long hora);

    List<QuadroHorario> findByTurmaId(Long turma);
}
