package com.escola.cadastro.escolar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Materia")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    private String nome;

    @ManyToOne
    private Pessoa professor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="materia_id")
    @JsonIgnore
    private List<QuadroHorario> sala;
}
