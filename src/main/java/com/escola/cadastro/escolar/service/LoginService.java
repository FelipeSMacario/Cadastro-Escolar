package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.LoginEntradaDTO;
import com.escola.cadastro.escolar.model.Login;
import com.escola.cadastro.escolar.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    LoginRepository loginRepository;

    @Autowired
    PasswordEncoder encoder;

    public ResponseEntity loganUsuario(LoginEntradaDTO entradaDTO){
        Optional<Login> login = loginRepository.findByUsuario(entradaDTO.getUsuario());
        return login.map(
                record -> {
                    return encoder.matches(entradaDTO.getSenha(), login.get().getSenha()) ?
                            ResponseEntity.ok().body(login) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
        ).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
