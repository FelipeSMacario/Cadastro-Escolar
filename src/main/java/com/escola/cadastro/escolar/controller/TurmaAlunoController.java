package com.escola.cadastro.escolar.controller;

import com.escola.cadastro.escolar.controller.api.TurmaAlunos;
import com.escola.cadastro.escolar.dto.AlunoTurmaDTO;
import com.escola.cadastro.escolar.dto.EntradaTurmaAlunoDTO;
import com.escola.cadastro.escolar.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "turmaAlunos")
public class TurmaAlunoController implements TurmaAlunos {

    @Autowired
    TurmaService turmaService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity cadastrarAlunoTurma(@RequestBody EntradaTurmaAlunoDTO entrada) {
        return turmaService.cadastrarAlunoTurma(entrada);
    }

    @GetMapping()
    public ResponseEntity listarAlunosTurma(){
        return turmaService.listarAlunosTurma();
    }

    @GetMapping(value = "buscarTurma/porNumero/{numero}")
    public ResponseEntity listarAlunosPorNumero(@PathVariable int numero){
        return turmaService.buscaAlunoPorNumero(numero);
    }

    @GetMapping(value = "buscarTurma/porNome/{nome}")
    public ResponseEntity listarturmaAlunoPorNome(@PathVariable String nome){
        return turmaService.listarturmaAlunoPorNome(nome);
    }

    @GetMapping(value = "/buscarTurma/{id}")
    public ResponseEntity listarTurmasAlunosPorId(@PathVariable Long id){
        return turmaService.listarTurmasAlunosPorId(id);
    }

    @GetMapping(value = "/buscarAluno/{matricula}")
    public ResponseEntity listarTurmasAlunos(@PathVariable Long matricula){
        return turmaService.listarTurmaPorMatricula(matricula);
    }

    @GetMapping(value = "/buscarAluno/porAno/{ano}")
    public ResponseEntity listarAlunosPorAno(@PathVariable Integer ano){
        return turmaService.listarAlunosPorAno(ano);
    }

    @GetMapping(value = "/buscarTurma/porMatricula/{matricula}")
    public ResponseEntity buscaTurmaPorMatricula(@PathVariable Long matricula){
        return turmaService.buscaTurmaPorMatricula(matricula);
    }

    @GetMapping(value = "/buscaTurma/porMatricula/porTurma/{matricula}/{turma}")
    public ResponseEntity buscaTurmaAlunoPorMatriculaRTurma(@PathVariable Long matricula, @PathVariable Long turma){
        return turmaService.buscaTurmaAluno(matricula, turma);
    }

    @DeleteMapping(value = "/removerAluno/{matricula}/{id}")
    public ResponseEntity deletarAlunoTurma(@PathVariable Long matricula, @PathVariable Long id){
        return turmaService.removerAlunoTurma(matricula, id);
    }

    @PutMapping(value = "/atualizarAlunoTurma")
    public ResponseEntity atualizarAlunoTurma(@RequestBody AlunoTurmaDTO entradaTurmaAlunoDTO){
        return turmaService.atualizarAlunoTurma(entradaTurmaAlunoDTO);
    }

    @GetMapping(value = "/buscaAluno/porTurma/{id}")
    public ResponseEntity buscaAlunoPorTurma(@PathVariable Long id){
        return turmaService.buscaAlunoPorTurma(id);
    }
}
