package com.example.Dias.model.response;

import com.example.Dias.model.Materia;
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
