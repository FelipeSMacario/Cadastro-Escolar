package com.escola.cadastro.escolar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendGridDTO {
    private String email;
    private String nome;
    private String cpf;
}
