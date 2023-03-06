package com.escola.cadastro.escolar.dto;

import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoTurmaDTO {
    private Pessoa aluno;
    private Turma turma;
}
