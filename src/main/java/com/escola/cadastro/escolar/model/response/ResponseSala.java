package com.escola.cadastro.escolar.model.response;

import com.escola.cadastro.escolar.model.Sala;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSala implements Serializable {
    private List<Sala> salas;
}
