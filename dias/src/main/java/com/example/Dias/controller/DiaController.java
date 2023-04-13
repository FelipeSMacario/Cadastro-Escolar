package com.example.Dias.controller;


import com.example.Dias.controller.api.DiaApi;
import com.example.Dias.service.DiaService;
import com.example.Dias.model.response.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "dia")
public class DiaController implements DiaApi {

    @Autowired
    DiaService diaService;
    @GetMapping(value = "/listar")
    public ResponseEntity<DefaultResponse> listarDias() {
        return diaService.listarDias();
    }
}
