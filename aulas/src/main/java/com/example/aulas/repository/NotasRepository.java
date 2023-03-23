package com.example.aulas.repository;

import com.example.aulas.model.Notas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotasRepository extends JpaRepository<Notas, Long> {

    @Query(value = "SELECT * FROM sistemaescolar.nota WHERE professor_matricula = :idProfessor AND aluno_matricula = :idAluno AND materia_id = :idMateria AND trimestre = :trimestre AND turma_id = :turmaId", nativeQuery = true)
    Optional<Notas> buscaNotas(@Param("idProfessor") Long idProfessor, @Param("idAluno") Long idAluno, @Param("idMateria") Long idMateria, @Param("trimestre") Integer trimestre, @Param("turmaId") Long turmaId);


    @Query(value = "SELECT nt.* FROM sistemaescolar.nota nt JOIN  sistemaescolar.pessoa pe ON nt.professor_matricula = pe.matricula " +
            "WHERE nt.professor_matricula = :idPessoa AND pe.status = 'Ativo' AND nt.semestre = :trimestre ", nativeQuery = true)
    List<Notas> buscaNotasProfessor(@Param("idPessoa") Long idPessoa, @Param("trimestre") Integer trimestre);

    @Query(value = "SELECT nt.* FROM sistemaescolar.nota nt JOIN  sistemaescolar.pessoa pe ON nt.aluno_matricula = pe.matricula " +
            "WHERE nt.aluno_matricula = :idPessoa AND pe.status = 'Ativo' AND nt.semestre = :trimestre ", nativeQuery = true)
    List<Notas> buscaNotasAluno(@Param("idPessoa") Long idPessoa, @Param("trimestre") Integer trimestre);

    List<Notas> findByTurmaIdAndMateriaId(Long id, Long id1);

    List<Notas> findByAlunoMatricula(Long matricula);
}
