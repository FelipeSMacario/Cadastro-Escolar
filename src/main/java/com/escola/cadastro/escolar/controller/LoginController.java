package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.dto.LoginEntradaDTO;
import com.escola.cadastro.escolar.model.response.AuthenticationResponse;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "login")
public class LoginController {
    @Autowired
    LoginService loginService;
    @PostMapping(value = "/logar")
    public ResponseEntity<DefaultResponse> logar(@RequestBody @Valid LoginEntradaDTO entradaDTO){
        return loginService.loganUsuario(entradaDTO);
    }
}
