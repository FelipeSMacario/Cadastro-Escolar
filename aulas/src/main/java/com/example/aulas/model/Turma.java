package com.example.aulas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Turma")
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false)
    private int numero;
    @NotNull
    @Min(0)
    @Max(3)
    @Column(name = "ano", nullable = false)
    private int ano;

    @OneToOne
    private Sala sala;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "TurmaAluno",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    @JsonIgnore
    private List<Pessoa> alunos = new ArrayList<>();
}
