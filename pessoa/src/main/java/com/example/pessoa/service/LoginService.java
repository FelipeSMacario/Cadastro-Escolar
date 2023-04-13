package com.example.pessoa.service;

import com.example.pessoa.dto.LoginDTO;
import com.example.pessoa.dto.RoleDTO;
import com.example.pessoa.model.Login;
import com.example.pessoa.repository.LoginRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    public LoginDTO logar(String username){
        List<RoleDTO> roleDTOList = new ArrayList<>();

        var login = loginRepository.findByUsuario(username).orElseThrow();
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(login, loginDTO);

        login.getRoles().forEach(valor -> roleDTOList.add(new RoleDTO(valor.getNome().name())));

        loginDTO.setRoles(roleDTOList);

        return loginDTO;
    }
}
