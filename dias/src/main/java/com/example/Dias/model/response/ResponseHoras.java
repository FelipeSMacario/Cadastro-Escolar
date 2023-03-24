package com.example.Dias.model.response;

import com.example.Dias.model.Horas;
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
