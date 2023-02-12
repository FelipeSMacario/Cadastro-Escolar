package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.dto.LoginEntradaDTO;
import com.escola.cadastro.escolar.model.response.AuthenticationResponse;
import com.escola.cadastro.escolar.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "login")
public class LoginController {
    @Autowired
    LoginService loginService;
    @PostMapping(value = "/logar")
    public AuthenticationResponse logar(@RequestBody LoginEntradaDTO entradaDTO){
        return loginService.loganUsuario(entradaDTO);
    }
}
