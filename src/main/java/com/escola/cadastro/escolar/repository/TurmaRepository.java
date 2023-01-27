package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    Optional<Turma> findByNumero(int numero);

    List<Turma> findByAno(int ano);

    Optional<Turma> findById(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sistemaescolar.turma_aluno WHERE aluno_id = :matricula", nativeQuery = true)
    void deletaAluno(@Param("matricula" )Long matricula);
}
