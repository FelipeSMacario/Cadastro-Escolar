package com.escola.cadastro.escolar.model;

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
@Table(name = "TurmaAluno")
public class TurmaAlunoProfessor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
