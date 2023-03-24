package com.example.Turma.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import com.example.Turma.dto.AlunoTurmaDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurmaAlunoResponse  implements Serializable {
    private List<AlunoTurmaDTO> alunoTurmaDTOList;
}
