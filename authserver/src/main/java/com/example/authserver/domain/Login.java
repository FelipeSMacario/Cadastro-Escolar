package com.example.authserver.domain;



import com.example.authserver.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Login")
public class Login{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario", unique = true, nullable = false)
    private String usuario;
    @Column(name = "senha", nullable = false)
    private String senha;
    @OneToOne()
    @NotNull
    private Pessoa pessoa;

    @Enumerated(EnumType.STRING)
    private RoleName roles;
}
