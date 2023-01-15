package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {
    Optional<Pessoa> findByMatriculaAndCargo(Long matricula,String cargo);

    List<Pessoa> findByCargoAndStatus(String cargo, String status);
}
