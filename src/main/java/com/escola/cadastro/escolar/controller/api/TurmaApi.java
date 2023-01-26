package com.escola.cadastro.escolar.controller.api;

import com.escola.cadastro.escolar.model.Turma;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Turma")
public interface TurmaApi {

    public ResponseEntity cadastrarTurma(@RequestBody Turma turma);

    public ResponseEntity listarTurmas();
    public ResponseEntity buscarCursoPorNumero(@PathVariable int numero);

    public ResponseEntity buscarCursoPorAno(@PathVariable int ano);

    public ResponseEntity atualizaTurma(@RequestBody Turma turma);
}
