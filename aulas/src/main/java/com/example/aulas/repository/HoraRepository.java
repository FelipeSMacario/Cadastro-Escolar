package com.example.aulas.repository;

import com.example.aulas.model.Horas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraRepository extends JpaRepository<Horas, Long> {
}
