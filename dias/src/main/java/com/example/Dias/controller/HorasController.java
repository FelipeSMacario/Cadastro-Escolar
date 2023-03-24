package com.example.Dias.controller;


import com.example.Dias.controller.api.HorasApi;
import com.example.Dias.service.HorasService;
import com.example.Dias.model.response.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "horas")
public class HorasController implements HorasApi {

    @Autowired
    HorasService horasService;
    @GetMapping("/listar")
    public ResponseEntity<DefaultResponse> listarHoras(){
        return horasService.listarHoras();
    }
}
