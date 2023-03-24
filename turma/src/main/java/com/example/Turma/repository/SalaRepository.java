package com.example.Turma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Turma.model.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
}
