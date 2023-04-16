package com.example.Dias.service;


import com.example.Dias.model.response.DefaultResponse;
import com.example.Dias.repository.DiasRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class DiaService {
    @Autowired
    DiasRepositry diasRepositry;

    public ResponseEntity<DefaultResponse> listarDias() {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data((Serializable) diasRepositry.findAll())
                .build());
    }
}
