package com.example.aulas.response;

import com.example.aulas.model.Horas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHoras implements Serializable {
    private List<Horas> horas;
}
