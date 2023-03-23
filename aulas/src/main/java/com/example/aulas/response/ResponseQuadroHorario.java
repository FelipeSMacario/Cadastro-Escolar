package com.example.aulas.response;

import com.example.aulas.model.QuadroHorario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseQuadroHorario implements Serializable {
    private List<QuadroHorario> quadroHorarios;
}
