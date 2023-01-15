package com.escola.cadastro.escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
    private Long matricula;
    private String cpf;
    private String nome;
    private String sobreNome;
    private LocalDate dataNascimento;
}
