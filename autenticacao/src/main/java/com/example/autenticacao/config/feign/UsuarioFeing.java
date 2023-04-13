package com.example.autenticacao.config.feign;

import com.example.autenticacao.model.Login;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "usuario", path = "/login")
public interface UsuarioFeing {
    @GetMapping("/logar/{username}")
    Login findEmail(@PathVariable String username);
}
