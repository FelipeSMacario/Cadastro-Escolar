package com.example.Dias.repository;

import com.example.Dias.model.Dia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiasRepositry extends JpaRepository<Dia, Long> {
}
