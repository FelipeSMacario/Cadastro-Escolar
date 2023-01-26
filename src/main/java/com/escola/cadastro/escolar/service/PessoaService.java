package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    PessoaRepository pessoaRepository;

    public ResponseEntity listar(String cargo) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findByCargoAndStatus(cargo, "Ativo"));
    }

    public ResponseEntity buscar(Long matricula, String cargo) {
        return pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo")
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity cadastrar(Pessoa pessoa, String cargo) {
        pessoaRepository.save(Pessoa.builder()
                .cpf(pessoa.getCpf())
                .nome(pessoa.getNome())
                .sobreNome(pessoa.getSobreNome())
                .dataNascimento(pessoa.getDataNascimento())
                .dataCadastro(LocalDate.now())
                .cargo(cargo)
                .status("Ativo").build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(cargo + " criado com sucesso");
    }

    public ResponseEntity atualizar(EntradaDTO entradaDTO, String cargo) {
        Optional<Pessoa> pessoa = pessoaRepository.findByMatriculaAndCargoAndStatus(entradaDTO.getMatricula(), cargo, "Ativo");

        return pessoa
                .map(record -> {
                    Pessoa professorAtualizado = pessoaRepository.save(Pessoa.builder()
                            .matricula(entradaDTO.getMatricula())
                            .cpf(entradaDTO.getCpf())
                            .nome(entradaDTO.getNome())
                            .sobreNome(entradaDTO.getSobreNome())
                            .dataNascimento(entradaDTO.getDataNascimento())
                            .dataCadastro(pessoa.get().getDataCadastro())
                            .status(entradaDTO.getStatus())
                            .cargo(pessoa.get().getCargo()).build());
                    return ResponseEntity.ok().body(professorAtualizado);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity buscarPorNome(String nome, String cargo) {
        return  ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findByNomeAndCargoAndStatus(nome, cargo, "Ativo"));

    }
}
