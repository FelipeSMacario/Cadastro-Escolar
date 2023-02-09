package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByUsuario(String usuario);
}
