package com.example.aulas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaQuadroAtualizarDTO {
    private Long idQuadro;
    private Long idDia;
    private Long idHora;

    private Long idTurma;

    private Long idMateria;

    private Long idSala;
}
