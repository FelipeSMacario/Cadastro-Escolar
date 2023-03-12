package com.escola.cadastro.escolar.model.response;

import com.escola.cadastro.escolar.model.Materia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMaterias implements Serializable {
    private List<Materia> materias;
}
