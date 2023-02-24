package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.LoginEntradaDTO;
import com.escola.cadastro.escolar.model.response.AuthenticationResponse;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.repository.LoginRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoginRepository loginRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<DefaultResponse> loganUsuario(LoginEntradaDTO entradaDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        entradaDTO.getUsuario(),
                        entradaDTO.getSenha()
                )
        );
        var user = loginRepository.findByUsuario(entradaDTO.getUsuario()).orElseThrow(() -> new ServiceException("Usuário não identificado"));

        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .pessoa(user.getPessoa())
                .token(jwtToken)
                .build();
        return ResponseEntity.ok().body(DefaultResponse.builder()
                        .success(true)
                        .messagem("Autenticado com sucesso!")
                        .status(HttpStatus.ACCEPTED)
                        .data(response)
                .build());
    }

}
