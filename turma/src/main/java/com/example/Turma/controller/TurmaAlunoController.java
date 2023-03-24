package com.example.Turma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Turma.controller.api.TurmaAlunos;
import com.example.Turma.dto.AlunoTurmaDTO;
import com.example.Turma.dto.EntradaTurmaAlunoDTO;
import com.example.Turma.service.TurmaAlunoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "turmaAlunos")
public class TurmaAlunoController implements TurmaAlunos  {

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

    @GetMapping(value = "/buscaAluno/porTurma/{id}")
    public ResponseEntity buscaAlunoPorTurma(@PathVariable Long id){
        return turmaAlunoService.buscaAlunoPorTurma(id);
    }

    @GetMapping(value = "/buscarTurma/{id}")
    public ResponseEntity buscarTurmaPorId(@PathVariable Long id){
        return turmaAlunoService.listarTurmasAlunosPorId(id);
    }

    @Override
    public ResponseEntity atualizarAlunoTurma(AlunoTurmaDTO entradaTurmaAlunoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarAlunoTurma'");
    }
}
