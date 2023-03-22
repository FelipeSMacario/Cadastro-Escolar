package com.example.pessoa.response;

import com.example.pessoa.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFiltroPessoaNome implements Serializable {
    private List<Pessoa> pessoaList;
}
