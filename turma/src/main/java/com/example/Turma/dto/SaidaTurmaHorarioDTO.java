package com.example.Turma.dto;


import com.example.Turma.model.Dia;
import com.example.Turma.model.Materia;
import com.example.Turma.model.Sala;
import com.example.Turma.model.Turma;
import com.example.Turma.model.response.Horas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaidaTurmaHorarioDTO {

    private Long id;
    private Turma turma;

    private Materia materia;

    private Horas horas;

    private Dia dia;

    private Sala sala;
}
