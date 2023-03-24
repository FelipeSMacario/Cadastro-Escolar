package com.example.Turma.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.example.Turma.model.response.Horas;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
