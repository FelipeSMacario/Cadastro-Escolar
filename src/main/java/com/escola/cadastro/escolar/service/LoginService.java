package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.LoginEntradaDTO;
import com.escola.cadastro.escolar.model.response.AuthenticationResponse;
import com.escola.cadastro.escolar.repository.LoginRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AuthenticationResponse loganUsuario(LoginEntradaDTO entradaDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        entradaDTO.getUsuario(),
                        entradaDTO.getSenha()
                )
        );
        var user = loginRepository.findByUsuario(entradaDTO.getUsuario()).orElseThrow(() -> new ServiceException("Usuário não identificado"));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .pessoa(user.getPessoa())
                .token(jwtToken)
                .build();
    }

}
