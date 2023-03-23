package com.example.aulas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Nota")
public class Notas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pessoa professor;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @Column(name = "dataInclusao", nullable = false)
    private LocalDate dataInclusao;

    @ManyToOne
    private Pessoa aluno;

    @Min(0)
    @Max(10)
    @Column(name = "nota", nullable = false)
    private Double nota;

    @Min(1)
    @Max(4)
    @Column(name = "trimestre", nullable = false)
    private Integer trimestre;

    @ManyToOne
    Turma turma;
}
