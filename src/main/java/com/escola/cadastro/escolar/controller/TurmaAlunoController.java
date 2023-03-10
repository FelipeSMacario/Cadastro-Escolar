package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.TurmaAlunos;
import com.escola.cadastro.escolar.dto.AlunoTurmaDTO;
import com.escola.cadastro.escolar.dto.EntradaTurmaAlunoDTO;
import com.escola.cadastro.escolar.service.TurmaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "turmaAlunos")
public class TurmaAlunoController implements TurmaAlunos {

    @Autowired
    TurmaAlunoService turmaAlunoService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarAlunoTurma(@RequestBody EntradaTurmaAlunoDTO entrada) {
        return turmaAlunoService.cadastrarAlunoTurma(entrada);
    }

    @GetMapping()
    public ResponseEntity listarAlunosTurma(){
        return turmaAlunoService.listarAlunosTurma();
    }

    @GetMapping(value = "buscarTurma/porNumero/{numero}")
    public ResponseEntity listarAlunosPorNumero(@PathVariable int numero){
        return turmaAlunoService.buscaAlunoPorNumero(numero);
    }

    @GetMapping(value = "buscarTurma/porNome/{nome}")
    public ResponseEntity listarturmaAlunoPorNome(@PathVariable String nome){
        return turmaAlunoService.listarturmaAlunoPorNome(nome);
    }

    @GetMapping(value = "/buscarAluno/{matricula}")
    public ResponseEntity listarTurmasAlunos(@PathVariable Long matricula){
        return turmaAlunoService.listarTurmaPorMatricula(matricula);
    }

    @GetMapping(value = "/buscarAluno/porAno/{ano}")
    public ResponseEntity listarAlunosPorAno(@PathVariable Integer ano){
        return turmaAlunoService.listarAlunosPorAno(ano);
    }

    @GetMapping(value = "/buscarTurma/porMatricula/{matricula}")
    public ResponseEntity buscaTurmaPorMatricula(@PathVariable Long matricula){
        return turmaAlunoService.buscaTurmaPorMatricula(matricula);
    }

    @GetMapping(value = "/buscaTurma/porMatricula/porTurma/{matricula}/{turma}")
    public ResponseEntity buscaTurmaAlunoPorMatriculaRTurma(@PathVariable Long matricula, @PathVariable Long turma){
        return turmaAlunoService.buscaTurmaAluno(matricula, turma);
    }

    @DeleteMapping(value = "/removerAluno/{matricula}/{id}")
    public ResponseEntity deletarAlunoTurma(@PathVariable Long matricula, @PathVariable Long id){
        return turmaAlunoService.removerAlunoTurma(matricula, id);
    }

    @PutMapping(value = "/atualizarAlunoTurma")
    public ResponseEntity atualizarAlunoTurma(@RequestBody AlunoTurmaDTO entradaTurmaAlunoDTO){
        return turmaAlunoService.atualizarAlunoTurma(entradaTurmaAlunoDTO);
    }

    @GetMapping(value = "/buscaAluno/porTurma/{id}")
    public ResponseEntity buscaAlunoPorTurma(@PathVariable Long id){
        return turmaAlunoService.buscaAlunoPorTurma(id);
    }
}
