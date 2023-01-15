package com.escola.cadastro.escolar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matricula;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobreNome;

    @Column(name = "dataNascimento")
    private LocalDate dataNascimento;

    @Column(name = "dataCadastro")
    private LocalDate dataCadastro;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "status")
    private String status;
}
