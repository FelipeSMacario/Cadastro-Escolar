package com.example.Turma.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Turma.model.response.Horas;

@Repository
public interface HoraRepository extends JpaRepository<Horas, Long> {
}
