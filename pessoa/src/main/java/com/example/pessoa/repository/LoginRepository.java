package com.example.pessoa.repository;

import com.example.pessoa.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByUsuario(String usuario);
}
