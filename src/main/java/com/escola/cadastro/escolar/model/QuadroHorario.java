package com.escola.cadastro.escolar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "QuadroHorario")
public class QuadroHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Turma turma;

    @ManyToOne
    Sala sala;

    @ManyToOne
    Materia materia;

    @ManyToOne
    Horas horas;

    @ManyToOne
    Dia dia;

}
