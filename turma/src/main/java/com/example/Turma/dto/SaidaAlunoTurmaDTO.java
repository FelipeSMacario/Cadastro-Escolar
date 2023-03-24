package com.example.Turma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import com.example.Turma.model.Pessoa;
import com.example.Turma.model.Turma;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaidaAlunoTurmaDTO implements Serializable {
    private Pessoa pessoa;

    private Turma turma;
}
