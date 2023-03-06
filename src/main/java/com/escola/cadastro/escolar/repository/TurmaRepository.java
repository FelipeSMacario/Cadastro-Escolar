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

    Optional<Turma> findByAlunosMatricula(Long matricula);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sistemaescolar.turma_aluno WHERE aluno_id = :matricula AND turma_id = :turma", nativeQuery = true)
    void deletaAluno(@Param("matricula" )Long matricula, @Param("turma" )Long turma);

    @Query(value = "SELECT ifnull(max(id), 0) + 1 FROM sistemaescolar.turma_aluno", nativeQuery = true)
    Long buscaIdMaximo();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sistemaescolar.turma_aluno VALUES (:idTurmaAluno, :idTurma, :alunoId)", nativeQuery = true)
    void cadastrarTurmaAluno(@Param("idTurmaAluno") Long idTurmaAluno, @Param("idTurma") Long idTurma, @Param("alunoId") Long alunoId);

    @Query(value = "SELECT count(aluno_id) FROM sistemaescolar.turma_aluno WHERE aluno_id = :matricula", nativeQuery = true)
    Optional<Integer> validaAluno(@Param("matricula") Long matricula);

    @Query(value = "SELECT aluno_id FROM sistemaescolar.turma_aluno WHERE turma_id = :turma", nativeQuery = true)
    List<Long> buscaAlunosPorTurma(@Param("turma") Long turma);

    @Query(value = "SELECT turma_id FROM sistemaescolar.turma_aluno WHERE aluno_id  = :matricula", nativeQuery = true)
    Optional<Long> buscaTurmaPorMatricula(@Param("matricula") Long matricula);

    @Query(value = "SELECT * FROM sistemaescolar.turma_aluno", nativeQuery = true)
    List<Object[]> buscaAlunoTurma();
}
