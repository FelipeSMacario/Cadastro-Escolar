package com.escola.cadastro.escolar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Clob;
import java.time.LocalDate;
import java.util.List;

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
    @ApiModelProperty(value = "CPF da pessoa", example = "15152736900", required = true, position = 1)
    private String cpf;

    @Column(name = "nome")
    @ApiModelProperty(value = "Nome da pessoa", example = "Carlos", required = true, position = 2)
    private String nome;

    @Column(name = "sobrenome")
    @ApiModelProperty(value = "Sobrenome da pessoa", example = "Da Sila", required = true, position = 3)
    private String sobreNome;

    @Column(name = "email")
    private String email;

    @Column(name = "dataNascimento")
    @ApiModelProperty(value = "Data de nascimento da pessoa", example = "2020-12-25", required = true, position = 4)
    private LocalDate dataNascimento;

    @Column(name = "foto", columnDefinition="longblob")
    @ApiModelProperty(value = "urlFoto")
    private String urlFoto;

    @Column(name = "dataCadastro")
    private LocalDate dataCadastro;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @ManyToMany(mappedBy = "alunos")
    private List<Turma> turmas;

    @Column(name = "ano")
    private Integer ano;

}
