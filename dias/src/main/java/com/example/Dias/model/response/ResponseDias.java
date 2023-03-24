package com.example.Dias.model.response;

import com.example.Dias.model.Dia;
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
