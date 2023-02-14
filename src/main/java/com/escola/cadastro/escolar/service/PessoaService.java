package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.enums.RoleName;
import com.escola.cadastro.escolar.model.Login;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Role;
import com.escola.cadastro.escolar.repository.LoginRepository;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    LoginRepository loginRepository;


    public ResponseEntity listar(String cargo) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findByCargoAndStatus(cargo, "Ativo"));
    }

    public ResponseEntity buscar(Long matricula, String cargo) {
        return pessoaRepository.findByMatriculaAndCargoAndStatus(matricula, cargo, "Ativo")
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity cadastrar(Pessoa pessoa, String cargo) {
        Pessoa pessoa1 = Pessoa.builder()
                .cpf(pessoa.getCpf())
                .nome(pessoa.getNome())
                .sobreNome(pessoa.getSobreNome())
                .email(pessoa.getEmail())
                .dataNascimento(pessoa.getDataNascimento())
                .urlFoto(pessoa.getUrlFoto())
                .dataCadastro(LocalDate.now())
                .cargo(cargo)
                .ano(pessoa.getAno())
                .status("Ativo").build();
        pessoaRepository.save(pessoa1);

        List<Role> roles = new ArrayList<>();
        Role role = new Role(1L, RoleName.ROLE_ADMIN);
        roles.add(role);

        Login login = Login.builder()
                .usuario(pessoa1.getEmail())
                .senha(encoder.encode(pessoa1.getCpf()))
                .pessoa(pessoa1)
                .roles(roles)
                .build();
        loginRepository.save(login);

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa1);
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
                                    .email(entradaDTO.getEmail())
                            .dataNascimento(entradaDTO.getDataNascimento())
                            .dataCadastro(pessoa.get().getDataCadastro())
                            .status(entradaDTO.getStatus())
                                    .urlFoto(entradaDTO.getUrlFoto())
                            .cargo(pessoa.get().getCargo()).build());
                    return ResponseEntity.ok().body(professorAtualizado);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity buscarPorNome(String nome, String cargo) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findByNomeAndCargoAndStatus(nome, cargo, "Ativo"));
    }


}
