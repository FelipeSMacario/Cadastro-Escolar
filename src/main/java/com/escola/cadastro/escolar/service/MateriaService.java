package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.model.response.ResponseMaterias;
import com.escola.cadastro.escolar.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MateriaService {
    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    ValidacoesService validacoesService;

    public ResponseEntity<DefaultResponse> listarMaterias() {
        List<Materia> materias = new ArrayList<>();
        try {
            materias = materiaRepository.findAll();
        }catch (Exception e){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .messagem("Erro ao buscar as matérias")
                    .build());
        }
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseMaterias(materias))
                .build());
    }

    public ResponseEntity<DefaultResponse> buscarMateriaPorNome(String nome) {
        Materia materia = validacoesService.buscaMateriaPorNome(nome);

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(materia)
                .build());

    }

    public ResponseEntity<DefaultResponse> cadastrarMateria(Materia materia) {
        Pessoa pessoa = validacoesService.buscaPessoa(materia.getProfessor().getMatricula(), "Professor");

        Materia materiaValidada =  materiaRepository.save(new Materia(null, materia.getNome(), pessoa));

        return ResponseEntity.ok().body(DefaultResponse.builder()
                        .success(true)
                        .messagem("Matéria cadastrada com sucesso")
                        .status(HttpStatus.CREATED)
                        .data(materiaValidada)
                .build());
    }

    public ResponseEntity<DefaultResponse> atualizarMateria(Materia materia) {
        Materia materia1 = validacoesService.buscaMateriaPorId(materia.getId());
        Materia materiaAtualizada = materiaRepository.save(
                Materia.builder()
                        .id(materia1.getId())
                        .nome(materia.getNome())
                        .professor(materia.getProfessor())
                        .build());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .messagem("Matéria atualizada com sucesso")
                .status(HttpStatus.OK)
                .data(materiaAtualizada)
                .build());
    }

    public ResponseEntity<DefaultResponse> deletarMateria(Long id) {
        Materia materia = validacoesService.buscaMateriaPorId(id);
        materiaRepository.deleteById(materia.getId());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                                        .success(true)
                        .messagem("Matéria deletada com sucesso")
                        .status(HttpStatus.OK)
                .build());
    }

}
