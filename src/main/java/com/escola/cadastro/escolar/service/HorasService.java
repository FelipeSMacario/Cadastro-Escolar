package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.repository.HoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HorasService {
    @Autowired
    HoraRepository horaRepository;


    public ResponseEntity listarHoras() {
        return ResponseEntity.ok().body(horaRepository.findAll());
    }
}
