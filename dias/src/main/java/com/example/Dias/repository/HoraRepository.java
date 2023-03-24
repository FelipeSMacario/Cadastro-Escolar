package com.example.Dias.repository;

import com.example.Dias.model.Horas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoraRepository extends JpaRepository<Horas, Long> {
}
