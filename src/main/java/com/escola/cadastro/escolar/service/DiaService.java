package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.repository.DiasRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DiaService {
    @Autowired
    DiasRepositry diasRepositry;

    public ResponseEntity listarDias() {
        return ResponseEntity.ok().body(diasRepositry.findAll());
    }
}
