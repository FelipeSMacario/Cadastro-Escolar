package com.example.aulas.response;

import com.example.aulas.model.Materia;
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
