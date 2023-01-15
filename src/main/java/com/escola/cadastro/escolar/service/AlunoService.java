package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.AlunoDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    PessoaRepository pessoaRepository;

    public ResponseEntity listarAlunos() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findByCargoAndStatus("Aluno", "Ativo"));
    }

    public ResponseEntity cadastrarAluno(Pessoa pessoa) {
        pessoaRepository.save(Pessoa.builder()
                .cpf(pessoa.getCpf())
                .nome(pessoa.getNome())
                .sobreNome(pessoa.getSobreNome())
                .dataNascimento(pessoa.getDataNascimento())
                .dataCadastro(LocalDate.now())
                .cargo("Aluno")
                .status("Ativo").build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("Aluno criado com sucesso");
    }

    public ResponseEntity buscarAluno(Long matricula) {
        return pessoaRepository.findByMatriculaAndCargo(matricula, "Aluno")
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity atualizarAluno(AlunoDTO alunoDTO) {
        Optional<Pessoa> pessoa = pessoaRepository.findByMatriculaAndCargo(alunoDTO.getMatricula(), "Aluno");

        return pessoa
                .map(record -> {
                    Pessoa alunoAtualizado = pessoaRepository.save(Pessoa.builder()
                            .matricula(alunoDTO.getMatricula())
                            .cpf(alunoDTO.getCpf())
                            .nome(alunoDTO.getNome())
                            .sobreNome(alunoDTO.getSobreNome())
                            .dataNascimento(alunoDTO.getDataNascimento())
                            .dataCadastro(LocalDate.now())
                            .status(pessoa.get().getStatus())
                            .cargo(pessoa.get().getCargo()).build());
                    return ResponseEntity.ok().body(alunoAtualizado);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity deletarAluno(Long matricula) {
        Optional<Pessoa> pessoa = pessoaRepository.findByMatriculaAndCargo(matricula, "Aluno");
        return pessoa
                .map(record -> {
                    pessoa.get().setStatus("Inativo");
                    pessoaRepository.save(pessoa.get());
                    return ResponseEntity.status(HttpStatus.CREATED).body("Aluno excluído com sucesso");
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
