package com.escola.cadastro.escolar.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag("Quadro de horários", "Gerencimanto do quadro de horários"),
                        new Tag("Alunos", "Gerencimanto de alunos"),
                        new Tag("Professor", "Gerencimanto de professores"),
                        new Tag("Materias", "Gerencimanto de matérias"),
                        new Tag("Notas", "Gerencimanto de notas"),
                        new Tag("Turma Alunos", "Gerencimanto do cadastro de alunos nas turmas"),
                        new Tag("Turma", "Gerencimanto de turmas"));
    }


}
