package com.example.pessoa.service;


import com.example.pessoa.dto.EntradaDTO;
import com.example.pessoa.dto.SendGridDTO;
import com.example.pessoa.enums.Fila;
import com.example.pessoa.enums.RoleName;
import com.example.pessoa.model.Login;
import com.example.pessoa.model.Pessoa;
import com.example.pessoa.model.Role;
import com.example.pessoa.repository.LoginRepository;
import com.example.pessoa.repository.PessoaRepository;
import com.example.pessoa.response.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    ValidacoesService validacoesService;

    @Autowired
    RabbitmqService rabbitmqService;

    public ResponseEntity<DefaultResponse> listar(String cargo,  Pageable pageable) {
            Page<Pessoa>  pessoaList = pessoaRepository.findByCargoAndStatus(cargo, "Ativo", pageable);
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(true)
                    .status(HttpStatus.OK)
                    .data((Serializable) pessoaList)
                    .build());
    }

    public ResponseEntity<DefaultResponse> buscar(Long matricula, String cargo) {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(validacoesService.buscaPessoa(matricula, cargo))
                .build());
    }

    public ResponseEntity<DefaultResponse> cadastrar(Pessoa pessoa, String cargo) {
        this.validaPessoa(pessoa);
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

            this.cadastrarLogin(pessoa1);
            this.disparaEmail(pessoa1);


            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(true)
                    .status(HttpStatus.CREATED)
                    .messagem(cargo + " cadastrado com sucesso!")
                    .data(pessoa1)
                    .build());

    }

    private void validaPessoa(Pessoa pessoa) {
        validacoesService.buscaPessoaCpf(pessoa.getCpf());
        validacoesService.buscaEmail(pessoa.getEmail());
    }

    public void cadastrarLogin(Pessoa pessoa){
        List<Role> roles = new ArrayList<>();

        Role role = pessoa.getCargo().equals("Professor") ? new Role(2L, RoleName.ROLE_PROFESSOR) : new Role(3L, RoleName.ROLE_ALUNO);
        roles.add(role);

        Login login = Login.builder()
                .usuario(pessoa.getEmail())
                .senha(encoder.encode(pessoa.getCpf()))
                .pessoa(pessoa)
                .roles(roles)
                .build();
        loginRepository.save(login);
    }

    public void disparaEmail(Pessoa pessoa){
        rabbitmqService.enviaMensagem(Fila.CADASTRO.toString(), new SendGridDTO(pessoa.getEmail(), pessoa.getNome(), pessoa.getCpf()));
    }

    public ResponseEntity<DefaultResponse> atualizar(EntradaDTO entradaDTO, String cargo) {
        Pessoa pessoa = validacoesService.buscaPessoa(entradaDTO.getMatricula(), cargo);
        validacoesService.validaAtualizacaoPessoa(pessoa);
        Pessoa pessoaAtualizada = pessoaRepository.save(Pessoa.builder()
                .matricula(entradaDTO.getMatricula())
                .cpf(entradaDTO.getCpf())
                .nome(entradaDTO.getNome())
                .sobreNome(entradaDTO.getSobreNome())
                .email(entradaDTO.getEmail())
                .ano(entradaDTO.getAno())
                .dataNascimento(entradaDTO.getDataNascimento())
                .dataCadastro(pessoa.getDataCadastro())
                .status(entradaDTO.getStatus())
                .urlFoto(entradaDTO.getUrlFoto())
                .cargo(pessoa.getCargo()).build());

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.ACCEPTED)
                .data(pessoaAtualizada)
                .build());
    }

    public ResponseEntity<DefaultResponse> buscarPorNome(String nome, String cargo, Pageable pageable) {
        Page<Pessoa> pessoaList = pessoaRepository.findByNomeAndCargoAndStatus(nome, cargo, "Ativo", pageable);

        if (pessoaList.isEmpty()) {
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .messagem("Nenhum " + cargo + " identificado com esse nome")
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data((Serializable) pessoaList)
                .build());
    }


    public Pessoa buscaPessoaSemFiltro(Long matricula) {
        return validacoesService.buscaPessoaSemCargo(matricula);
    }

    public ResponseEntity<DefaultResponse> listarSemPaginacao(String cargo) {
        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data((Serializable) pessoaRepository.findByCargoAndStatus(cargo, "Ativo"))
                .build());
    }
}
