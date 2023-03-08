package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.EntradaDTO;
import com.escola.cadastro.escolar.enums.RoleName;
import com.escola.cadastro.escolar.exception.UserNotFoundException;
import com.escola.cadastro.escolar.model.Login;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Role;
import com.escola.cadastro.escolar.model.response.DefaultResponse;
import com.escola.cadastro.escolar.model.response.ResponseFiltroPessoaNome;
import com.escola.cadastro.escolar.repository.LoginRepository;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import io.github.cdimascio.dotenv.Dotenv;


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

    public void sendEmailToUser(String email, String name, String password) throws IOException {

        System.out.println("Sending registration email to " + email);

        Dotenv dotenv = Dotenv.load();

        Email from = new Email("brunofonseca821@gmail.com");
        String subject = "Cadastro realizado";
        Email to = new Email(email);
        Content content = new Content("text/html", " ");


        Mail mail = new Mail(from, subject, to, content);
        
        Personalization personalization0 = new Personalization();
        personalization0.addDynamicTemplateData("name", name);
        personalization0.addDynamicTemplateData("email", email);
        personalization0.addDynamicTemplateData("password", password);

        personalization0.addTo(new Email(email));
        

        mail.addPersonalization(personalization0);

        mail.setTemplateId("d-51c075f4bf2445a284e335a46131cdb8");

        
        SendGrid sg = new SendGrid(dotenv.get("SENDGRID_API_KEY"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

            System.out.println("Email to user " + email + " sent successfully");

        } catch (IOException ex) {
            System.out.println("Error when trying to send email to:" + email);
            throw ex;
        }
       
    }


    public ResponseEntity<DefaultResponse> listar(String cargo) {
        List<Pessoa> pessoaList = new ArrayList<>();
        try {
            pessoaList = pessoaRepository.findByCargoAndStatus(cargo, "Ativo");
        }catch (Exception e){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .messagem(e.getMessage())
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseFiltroPessoaNome(pessoaList))
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

        Role role = pessoa1.getCargo().equals("Professor") ? new Role(2L, RoleName.ROLE_PROFESSOR) : new Role(3L, RoleName.ROLE_ALUNO);
        roles.add(role);

        try {
            sendEmailToUser(pessoa.getEmail(), pessoa.getNome(), pessoa1.getCpf());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Login login = Login.builder()
                .usuario(pessoa1.getEmail())
                .senha(encoder.encode(pessoa1.getCpf()))
                .pessoa(pessoa1)
                .roles(roles)
                .build();
        loginRepository.save(login);

        return ResponseEntity.ok().body(DefaultResponse.builder()
                        .success(true)
                        .status(HttpStatus.CREATED)
                        .messagem(cargo + " cadastrado com sucesso!")
                        .data(pessoa1)
                .build());
    }

    public ResponseEntity<DefaultResponse> atualizar(EntradaDTO entradaDTO, String cargo) {
        Pessoa pessoa = validacoesService.buscaPessoa(entradaDTO.getMatricula(), cargo);
        Pessoa pessoaAtualizada = pessoaRepository.save(Pessoa.builder()
                .matricula(entradaDTO.getMatricula())
                .cpf(entradaDTO.getCpf())
                .nome(entradaDTO.getNome())
                .sobreNome(entradaDTO.getSobreNome())
                .email(entradaDTO.getEmail())
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

    public ResponseEntity<DefaultResponse> buscarPorNome(String nome, String cargo) {
        List<Pessoa> pessoaList = pessoaRepository.findByNomeAndCargoAndStatus(nome, cargo, "Ativo");

        if(pessoaList.isEmpty()){
            return ResponseEntity.ok().body(DefaultResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND)
                    .messagem("Nenhum " + cargo + " identificado com esse nome")
                    .build());
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(new ResponseFiltroPessoaNome(pessoaList))
                .build());
    }



}
