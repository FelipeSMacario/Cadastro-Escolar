package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.TurmaApi;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "turma")
public class TurmaController implements TurmaApi {

    @Autowired
    TurmaService turmaService;

    @GetMapping(value = "/listar")
    public ResponseEntity<DefaultResponse> listarTurmas(){
        return turmaService.listarTurmas();
    }


    @GetMapping(value = "/buscar/porNumero/{numero}")
    public ResponseEntity<DefaultResponse> buscarTurmaPorNumero(@PathVariable int numero){
        return turmaService.buscaPorNumero(numero);
    }

    @GetMapping(value = "/buscar/porAno/{ano}")
    public ResponseEntity<DefaultResponse> buscarTurmaPorAno(@PathVariable int ano){
        return turmaService.buscaPorAno(ano);
    }



}
