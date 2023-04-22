package com.example.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api-dias/**", "/api-turma-client/**", "/api-aulas/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api-dias/**", "/api-turma-client/**","/api-aulas/quadroHorario/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api-turma-client/**","/api-aulas/quadroHorario/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api-turma-client/**","/api-aulas/quadroHorario/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api-aulas/notas/**").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.PUT,"/api-aulas/notas/**").hasAnyRole("ADMIN", "PROFESSOR")

                .antMatchers("/usuario/alunos/listar",
                                        "/usuario/alunos/buscar/porNome/{nome}",
                                        "/usuario/alunos/cadastrar").hasRole("ADMIN")

                .antMatchers("/usuario/alunos/buscar/{matricula}",
                                        "/usuario/alunos/atualizar").hasAnyRole("ADMIN", "ALUNO")

                .antMatchers("/usuario/professor/listar",
                                        "/usuario/professor/listarSemPaginacao",
                                        "/usuario/professor/buscar/porNome/{nome}",
                                        "/usuario/professor/cadastrar").hasRole("ADMIN")

                .antMatchers("/usuario/professor/buscar/{matricula}",
                                        "/usuario/professor/atualizar").hasAnyRole("ADMIN", "PROFESSOR")
                .anyRequest().authenticated();

        http.cors().configurationSource(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("*"));
        corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean
                = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };
    }