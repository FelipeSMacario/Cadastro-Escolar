package com.escola.cadastro.escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotasTrimestreDTO {
    private Long matriculaProfessor;
    private Long matriculaAluno;
    private String materia;
    private Double nota;
    private Integer trimeste;

}