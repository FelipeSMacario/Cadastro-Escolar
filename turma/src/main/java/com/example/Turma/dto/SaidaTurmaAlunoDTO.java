package com.example.Turma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.Turma.model.Pessoa;
import com.example.Turma.model.Turma;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaidaTurmaAlunoDTO {
    private Turma turma;

    private List<Pessoa> alunos;
}
