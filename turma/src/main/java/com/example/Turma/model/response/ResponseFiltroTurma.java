package com.example.Turma.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import com.example.Turma.model.Turma;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFiltroTurma implements Serializable {
    private List<Turma> turmaList;
}
