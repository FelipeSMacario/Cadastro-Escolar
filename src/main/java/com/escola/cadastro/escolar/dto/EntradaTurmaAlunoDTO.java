package com.escola.cadastro.escolar.dto;

import com.escola.cadastro.escolar.model.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntradaTurmaAlunoDTO {
    private List<PessoaEntradaDTO> pessoas;

    private Long turmaId;

}
