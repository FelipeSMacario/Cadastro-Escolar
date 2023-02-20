package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
