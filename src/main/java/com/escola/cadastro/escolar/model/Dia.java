package com.escola.cadastro.escolar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Dia")
public class Dia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    @ApiModelProperty(value = "nome do dia da semana", example = "Segunda-Feira", required = true, position = 0)
    private String nome;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="dia_id")
    @JsonIgnore
    private List<QuadroHorario> sala;


}
