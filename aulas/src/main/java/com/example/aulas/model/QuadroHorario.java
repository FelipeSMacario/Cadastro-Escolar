package com.example.aulas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "QuadroHorario")
public class QuadroHorario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Turma turma;
    @ManyToOne
    Materia materia;

    @ManyToOne
    Horas horas;

    @ManyToOne
    Dia dia;

}
