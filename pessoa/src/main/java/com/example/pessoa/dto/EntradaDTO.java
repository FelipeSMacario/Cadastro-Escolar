package com.example.pessoa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaDTO {

    @NotNull
    private Long matricula;
    @NotNull
    @NotEmpty
    @NotBlank
    @CPF(message = "CPF invalído")
    private String cpf;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 50)
    private String nome;
    @NotNull
    @Min(0)
    @Max(3)
    private Integer ano;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 50)
    private String sobreNome;

    @Email
    @NotNull
    @Length(max = 50)
    private String email;
    @NotNull
    private LocalDate dataNascimento;
    private String urlFoto;

    @NotNull
    @NotEmpty
    @NotBlank
    private String status;
}
