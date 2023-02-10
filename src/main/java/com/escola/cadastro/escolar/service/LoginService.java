package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.LoginEntradaDTO;
import com.escola.cadastro.escolar.model.Login;
import com.escola.cadastro.escolar.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LoginService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> login = loginRepository.findByUsuario(username);
        return new User(login.get().getUsuario(), login.get().getSenha(), true, true, true,true, login.get().getAuthorities());
    }
}
