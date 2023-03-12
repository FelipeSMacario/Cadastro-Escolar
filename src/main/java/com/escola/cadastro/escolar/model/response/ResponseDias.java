package com.escola.cadastro.escolar.model.response;

import com.escola.cadastro.escolar.model.Dia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDias implements Serializable {
    private List<Dia> dias;
}
