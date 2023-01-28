package com.escola.cadastro.escolar.repository;

import com.escola.cadastro.escolar.model.Horas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraRepository extends JpaRepository<Horas, Long> {
}
