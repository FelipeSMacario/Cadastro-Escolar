package com.example.aulas.repository;

import com.example.aulas.model.Dia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiasRepositry extends JpaRepository<Dia, Long> {
}
