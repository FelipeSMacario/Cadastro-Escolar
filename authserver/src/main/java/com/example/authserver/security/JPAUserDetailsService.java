package com.example.authserver.security;


import com.example.authserver.domain.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPAUserDetailsService implements UserDetailsService{

    @Autowired
    LoginRepository loginRepository;


    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        final var user = loginRepository.findByUsuario(usuario)
                .orElseThrow(()-> new UsernameNotFoundException(usuario));

        final var simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRoles().name());
        System.out.println(simpleGrantedAuthority);

        return new User(
                user.getUsuario(),
                user.getSenha(),
                List.of(simpleGrantedAuthority)
        );
    }
}
