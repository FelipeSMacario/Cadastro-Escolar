package com.example.aulas.repository;

import com.example.aulas.model.QuadroHorario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<QuadroHorario, Long> {
    List<QuadroHorario> findByDiaIdAndTurmaId(Long dia, Long hora);

    List<QuadroHorario> findByDiaIdAndHorasId(Long dia, Long hora);

    Page<QuadroHorario> findByTurmaNumero(Long turma, Pageable pageable);

    Page<QuadroHorario> findByTurmaId(Long turma, Pageable pageable);
}

