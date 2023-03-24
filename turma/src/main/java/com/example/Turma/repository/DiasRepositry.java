package com.example.Turma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Turma.model.Dia;

@Repository
public interface DiasRepositry extends JpaRepository<Dia, Long> {
}
