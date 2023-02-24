package com.escola.cadastro.escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaQuadroHorarioDTO implements Serializable {
    private Long idDia;
    private Long idHora;

    private Long idTurma;

    private Long idMateria;

    private Long idSala;
}
