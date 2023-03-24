package com.example.Dias.service;


import com.example.Dias.model.Dia;
import com.example.Dias.model.response.DefaultResponse;
import com.example.Dias.model.response.ResponseDias;
import com.example.Dias.repository.DiasRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiaService {
    @Autowired
    DiasRepositry diasRepositry;

    public ResponseEntity<DefaultResponse> listarDias() {
        List<Dia> dias = new ArrayList<>();
        try {
            dias = diasRepositry.findAll();
        }catch (Exception e){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .messagem("Erro ao buscar os dias")
                    .build());
        }
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseDias(dias))
                .build());
    }
}
