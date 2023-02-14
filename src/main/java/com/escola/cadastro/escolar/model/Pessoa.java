package com.escola.cadastro.escolar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotNull
    @NotEmpty
    @NotBlank
    //@CPF
    @Length(min = 11, max = 11)
    @Column(name = "cpf")
    @ApiModelProperty(value = "CPF da pessoa", example = "15152736900", required = true, position = 1)
    private String cpf;

    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "nome")
    @ApiModelProperty(value = "Nome da pessoa", example = "Carlos", required = true, position = 2)
    private String nome;

    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "sobrenome")
    @ApiModelProperty(value = "Sobrenome da pessoa", example = "Da Sila", required = true, position = 3)
    private String sobreNome;

    @Email
    @NotNull
    @Length(max = 50)
    @Column(name = "email")
    private String email;

    @NotNull
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

    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ano")
    private Integer ano;

}
