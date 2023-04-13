package com.example.pessoa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String usuario;

    private Long matricula;
    private String senha;
    private List<RoleDTO> roles;

}
