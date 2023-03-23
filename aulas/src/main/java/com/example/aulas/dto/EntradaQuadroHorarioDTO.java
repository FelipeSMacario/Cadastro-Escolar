package com.example.aulas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaQuadroHorarioDTO {
    private Long idDia;
    private Long idHora;

    private Long idTurma;

    private Long idMateria;

    private Long idSala;
}
