package com.example.Turma.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Turma.model.Pessoa;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {
    Optional<Pessoa> findByMatriculaAndCargoAndStatus(Long matricula,String cargo, String status);

    List<Pessoa> findByNomeAndCargoAndStatus(String nome,String cargo, String status);

    Optional<Pessoa> findByMatriculaAndStatus(Long matricula, String status);

    List<Pessoa> findByCargoAndStatus(String cargo, String status);

    List<Pessoa> findByCargoAndStatusAndAno(String cargo, String status, Integer ano);
}
