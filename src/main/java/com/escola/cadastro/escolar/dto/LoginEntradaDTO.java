package com.escola.cadastro.escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntradaDTO {
    private String usuario;
    private String senha;
}
