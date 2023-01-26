package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    Optional<Turma> findByNumero(int numero);

    List<Turma> findByAno(int ano);
}
