package com.escola.cadastro.escolar.dto;

import com.escola.cadastro.escolar.model.*;
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
