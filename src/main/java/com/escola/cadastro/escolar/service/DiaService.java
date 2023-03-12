package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.model.Dia;
import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.model.response.ResponseDias;
import com.escola.cadastro.escolar.model.response.ResponseMaterias;
import com.escola.cadastro.escolar.repository.DiasRepositry;
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
