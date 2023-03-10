package com.escola.cadastro.escolar.model.response;

import com.escola.cadastro.escolar.model.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFiltroTurma implements Serializable {
    private List<Turma> turmaList;
}
