package com.example.pessoa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaDTO {
    private Long matricula;
    private String cpf;
    private String nome;
    private Integer ano;
    private String sobreNome;
    private String email;
    private LocalDate dataNascimento;
    private String urlFoto;
    private String status;
}
