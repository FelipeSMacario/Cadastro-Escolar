package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.Dia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiasRepositry extends JpaRepository<Dia, Long> {
}
