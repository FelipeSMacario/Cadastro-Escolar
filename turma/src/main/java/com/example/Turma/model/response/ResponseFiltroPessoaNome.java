package com.example.Turma.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import com.example.Turma.model.Pessoa;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFiltroPessoaNome implements Serializable {
    private List<Pessoa> pessoaList;
}
