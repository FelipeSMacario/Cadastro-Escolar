package com.escola.cadastro.escolar.dto;

import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaidaTurmaAlunoDTO {
    private Turma turma;

    private List<Pessoa> alunos;
}
