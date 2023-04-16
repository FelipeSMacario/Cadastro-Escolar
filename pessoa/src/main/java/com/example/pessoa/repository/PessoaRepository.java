package com.example.pessoa.repository;

import com.example.pessoa.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByMatriculaAndCargoAndStatus(Long matricula,String cargo, String status);

    Page<Pessoa> findByNomeAndCargoAndStatus(String nome,String cargo, String status, Pageable pageable);

    Page<Pessoa> findByCargoAndStatus(String cargo, String status, Pageable pageable);

    List<Pessoa> findByCpf(String cpf);

    List<Pessoa> findByEmail(String email);

    List<Pessoa> findByCargoAndStatus(String cargo, String status);
}
