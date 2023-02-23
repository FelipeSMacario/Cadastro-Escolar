package com.escola.cadastro.escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntradaDTO {
    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    private String usuario;
    @NotNull
    @NotBlank
    @NotEmpty
    private String senha;
}
