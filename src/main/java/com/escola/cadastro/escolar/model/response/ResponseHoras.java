package com.escola.cadastro.escolar.model.response;

import com.escola.cadastro.escolar.model.Horas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHoras  implements Serializable {
    private List<Horas> horas;
}
