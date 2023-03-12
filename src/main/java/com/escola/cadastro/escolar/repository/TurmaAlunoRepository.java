package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.Turma;
import com.escola.cadastro.escolar.model.TurmaAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaAlunoRepository extends JpaRepository<TurmaAluno, Long> {

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

    @Query(value = "SELECT ifnull(max(turma_id), 0) FROM sistemaescolar.turma_aluno WHERE aluno_id  = :matricula", nativeQuery = true)
    Optional<Long> buscaTurmaPorMatricula(@Param("matricula") Long matricula);

    @Query(value = "SELECT * FROM sistemaescolar.turma_aluno", nativeQuery = true)
    List<Object[]> buscaAlunoTurma();

    @Query(value = "SELECT * FROM sistemaescolar.turma_aluno WHERE aluno_id = :matricula AND turma_id = :turma", nativeQuery = true)
    List<Object[]> definaAlunoTurma(@Param("matricula" )Long matricula, @Param("turma" )Long turma);

    @Query(value = "SELECT * FROM sistemaescolar.turma_aluno WHERE id = :id", nativeQuery = true)
    List<Object[]> definaAlunoTurmaPorId(@Param("id" ) Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE sistemaescolar.turma_aluno SET turma_id = :idTurma WHERE id = :id ", nativeQuery = true)
    void atualizaAlunoTurma(@Param("id") Long id, @Param("idTurma") Long idTurma);
}
