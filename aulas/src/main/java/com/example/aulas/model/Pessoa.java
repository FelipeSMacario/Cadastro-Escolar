package com.example.aulas.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Pessoa")
public class Pessoa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matricula;

    @NotNull
    @NotEmpty
    @NotBlank
//    @CPF
    @Length(min = 11, max = 11)
    @Column(name = "cpf", nullable = false)
    @ApiModelProperty(value = "CPF da pessoa", example = "15152736900", required = true, position = 1)
    private String cpf;

    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "nome", nullable = false)
    @ApiModelProperty(value = "Nome da pessoa", example = "Carlos", required = true, position = 2)
    private String nome;

    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "sobrenome", nullable = false)
    @ApiModelProperty(value = "Sobrenome da pessoa", example = "Da Sila", required = true, position = 3)
    private String sobreNome;

    @Email
    @NotNull
    @Length(max = 50)
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "dataNascimento", nullable = false)
    @ApiModelProperty(value = "Data de nascimento da pessoa", example = "2020-12-25", required = true, position = 4)
    private LocalDate dataNascimento;

    @Column(name = "foto", columnDefinition="longblob")
    @ApiModelProperty(value = "urlFoto")
    private String urlFoto;

    @Column(name = "dataCadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "status", nullable = false)
    private String status;

    @JsonIgnore
    @ManyToMany(mappedBy = "alunos")
    private List<Turma> turmas;

    @NotNull
    @Min(0)
    @Max(3)
    @Column(name = "ano", nullable = false)
    private Integer ano;

}
