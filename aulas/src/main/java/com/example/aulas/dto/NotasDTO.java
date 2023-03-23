package com.example.aulas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotasDTO {
    private Long matriculaProfessor;
    private List<MatriculaNotasDTO> matriculasNotas;
    private String materia;

    private Long turmaId;

}
