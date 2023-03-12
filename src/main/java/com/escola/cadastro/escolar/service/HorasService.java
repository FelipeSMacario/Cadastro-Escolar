package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.model.Horas;
import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.model.response.ResponseHoras;
import com.escola.cadastro.escolar.model.response.ResponseMaterias;
import com.escola.cadastro.escolar.repository.HoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HorasService {
    @Autowired
    HoraRepository horaRepository;

    public ResponseEntity<DefaultResponse> listarHoras() {
        List<Horas> horas = new ArrayList<>();
        try {
            horas = horaRepository.findAll();
        }catch (Exception e){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .messagem("Erro ao buscar as horas")
                    .build());
        }
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseHoras(horas))
                .build());
    }
}
