//package com.example.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class Gateway {
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route("aluno", r ->
//                        r.path("/alunos/**")
//                                .uri("lb://api-pessoa"))
//                .route("professor", r ->
//                        r.path("/professor/**")
//                                .uri("lb://api-pessoa"))
//                .route("aulas", r ->
//                        r.path("/quadroHorario/**")
//                                .uri("lb://api-aulas"))
//                .route("notas", r ->
//                        r.path("/notas/**")
//                                .uri("lb://api-aulas"))
//                .route("dia", r ->
//                        r.path("/dia/**")
//                                .uri("lb://api-dias"))
//                .route("horas", r ->
//                        r.path("/horas/**")
//                                .uri("lb://api-dias"))
//                .route("materia", r ->
//                        r.path("/materias/**")
//                                .uri("lb://api-dias"))
//                .route("sala", r ->
//                        r.path("/sala/**")
//                                .uri("lb://api-turma-client"))
//                .route("turma", r ->
//                        r.path("/turma/**")
//                                .uri("lb://api-turma-client"))
//                .route("turmaAluno", r ->
//                        r.path("/turmaAlunos/**")
//                                .uri("lb://api-turma-client"))
//                .build();
//    }
//}
