package com.escola.cadastro.escolar.service;

import com.escola.cadastro.escolar.dto.NotasDTO;
import com.escola.cadastro.escolar.dto.NotasPessoaDTO;
import com.escola.cadastro.escolar.dto.NotasSaidaDTO;
import com.escola.cadastro.escolar.dto.NotasTrimestreDTO;
import com.escola.cadastro.escolar.exception.*;
import com.escola.cadastro.escolar.model.Materia;
import com.escola.cadastro.escolar.model.Notas;
import com.escola.cadastro.escolar.model.Pessoa;
import com.escola.cadastro.escolar.model.Turma;
import com.escola.cadastro.escolar.repository.MateriaRepository;
import com.escola.cadastro.escolar.repository.NotasRepository;
import com.escola.cadastro.escolar.repository.PessoaRepository;
import com.escola.cadastro.escolar.repository.TurmaRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotasService {
    @Autowired
    NotasRepository notasRepository;
    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    ValidacoesService validacoesService;

    public void sendEmailNotasToUser(String email, String name, String nota, String materia) throws java.io.IOException {

        System.out.println("Sending nota email to " + email);

        Dotenv dotenv = Dotenv.load();

        Email from = new Email("brunofonseca821@gmail.com");
        String subject = "Cadastro realizado";
        Email to = new Email(email);
        Content content = new Content("text/html", " ");

        Mail mail = new Mail(from, subject, to, content);

        Personalization personalization0 = new Personalization();
        personalization0.addDynamicTemplateData("name", name);
        personalization0.addDynamicTemplateData("email", email);
        personalization0.addDynamicTemplateData("nota", nota);
        personalization0.addDynamicTemplateData("materia", materia);

        personalization0.addTo(new Email(email));

        mail.addPersonalization(personalization0);

        mail.setTemplateId("d-c810e44a8a484641aa82cf572f750387");

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

    public ResponseEntity cadastrarNotas(NotasDTO notasDTO) {
        Pessoa professor = validacoesService.buscaPessoa(notasDTO.getMatriculaProfessor(), "Professor");
        Materia materia = validacoesService.buscaMateriaPorNome(notasDTO.getMateria());
        Turma turma = validacoesService.buscaTurma(notasDTO.getTurmaId());

        notasDTO.getMatriculasNotas().forEach(valor -> {
            Pessoa aluno = validacoesService.buscaPessoa(valor.getMatriculaAluno(), "Aluno");
            validaTrimeste(professor.getMatricula(), aluno.getMatricula(), materia.getId(), buscaTrimeste(), notasDTO.getTurmaId());
            Notas notas = Notas.builder()
                    .nota(valor.getNotas())
                    .professor(professor)
                    .turma(turma)
                    .aluno(aluno)
                    .materia(materia)
                    .dataInclusao(LocalDate.now())
                    .trimestre(buscaTrimeste()).build();
            notasRepository.save(notas);

            try {
                sendEmailNotasToUser(aluno.getEmail(), aluno.getNome(), valor.getNotas().toString(), materia.getNome());
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

        });

        return ResponseEntity.ok().body(notasDTO);
    }

    public ResponseEntity alterarNotas(NotasTrimestreDTO notasDTO) {
        Notas notas = validacoesService.buscaNotas(notasDTO.getNotaId());
        Notas notaAtualizada = notasRepository.save(Notas.builder()
                .id(notas.getId())
                .professor(notas.getProfessor())
                .aluno(notas.getAluno())
                .materia(notas.getMateria())
                .trimestre(notas.getTrimestre())
                .turma(notas.getTurma())
                .dataInclusao(notas.getDataInclusao())
                .nota(notasDTO.getNota())
                .build());

        return ResponseEntity.ok().body(notaAtualizada);
    }

    public ResponseEntity deletarNotas(NotasTrimestreDTO notasDTO) {
        Notas notas = validacoesService.buscaNotas(notasDTO.getNotaId());
        notasRepository.deleteById(notas.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Nota deletada com sucesso");
    }

    public ResponseEntity listarNotas(NotasPessoaDTO notasPessoaDTO) {
        Optional<Pessoa> pessoa = pessoaRepository.findByMatriculaAndStatus(notasPessoaDTO.getMatriculaPessoa(), "Ativo");
        return pessoa
                .map(record -> {
                    List<Notas> notas = record.getCargo().equals("Professor")
                            ? notasRepository.buscaNotasProfessor(notasPessoaDTO.getMatriculaPessoa(), notasPessoaDTO.getTrimeste())
                            : notasRepository.buscaNotasAluno(notasPessoaDTO.getMatriculaPessoa(), notasPessoaDTO.getTrimeste());

                    List<NotasSaidaDTO> notasSaidaDTOS = new ArrayList<>();
                    notas.forEach(v -> notasSaidaDTOS.add(
                            NotasSaidaDTO.builder()
                                    .nome(pessoa.get().getNome())
                                    .sobreNome(pessoa.get().getSobreNome())
                                    .materia(v.getMateria().getNome())
                                    .nota(v.getNota()).build()
                    ));
                    return ResponseEntity.ok().body(notasSaidaDTOS);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity buscaNotaPorId(Long id) {
        return ResponseEntity.ok().body( validacoesService.buscaNotas(id));
    }

    public ResponseEntity buscaNotasPorTurmaAMateria(Long idTurma, Long idMateria) {
        Turma turma = validacoesService.buscaTurma(idTurma);
        Materia materia = validacoesService.buscaMateriaPorId(idMateria);

        return ResponseEntity.ok().body(notasRepository.findByTurmaIdAndMateriaId(turma.getId(), materia.getId()));
    }

    public ResponseEntity buscaPorMatricula(Long matricula) {
        Pessoa aluno = validacoesService.buscaPessoa(matricula, "Aluno");
        return ResponseEntity.ok().body(notasRepository.findByAlunoMatricula(aluno.getMatricula()));
    }

    private Integer buscaTrimeste() {
        LocalDate datas = LocalDate.now();
        if (datas.isAfter(LocalDate.of(LocalDate.now().getYear(), 1, 1)) && datas.isBefore(LocalDate.of(LocalDate.now().getYear(), 3, 31)))
            return 1;

        if (datas.isAfter(LocalDate.of(LocalDate.now().getYear(), 4, 1)) && datas.isBefore(LocalDate.of(LocalDate.now().getYear(), 6, 30)))
            return 2;

        if (datas.isAfter(LocalDate.of(LocalDate.now().getYear(), 7, 1)) && datas.isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 30)))
            return 3;

        if (datas.isAfter(LocalDate.of(LocalDate.now().getYear(), 10, 1)) && datas.isBefore(LocalDate.of(LocalDate.now().getYear(), 12, 31)))
            return 4;

        return 0;

    }

    private void validaTrimeste(Long idProfessor, Long idAluno, Long idMateria, Integer semestre, Long idTurma) {
        Optional<Notas> notas = notasRepository.buscaNotas(idProfessor, idAluno, idMateria, semestre, idTurma);
        if (notas.isPresent())
            throw new NotasAlreadyExistsException(notas.get().getId());
    }
}
