package com.email.ms.emailms.consumer;

import com.email.ms.emailms.dto.SendGridDTO;
import com.email.ms.emailms.enums.StatusEmail;
import com.email.ms.emailms.model.EmailModel;
import com.email.ms.emailms.repository.EmailRepository;
import com.google.gson.Gson;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class EmailConsumer {

    @Autowired
    EmailRepository emailRepository;
    Gson gson = new Gson();



    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload String mensagem){
        SendGridDTO emailDto = gson.fromJson(mensagem, SendGridDTO.class);
        try {
            sendEmailToUser(emailDto.getEmail(), emailDto.getNome(), emailDto.getCpf());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

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
        EmailModel emailModel = new EmailModel();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

            emailModel.setEmailFrom("brunofonseca821@gmail.com");
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailTo(email);
            emailModel.setSubject(subject);
            emailModel.setOwnerRef("Cadastro escolar");
            emailModel.setStatusEmail(StatusEmail.SENT);

            System.out.println("Email to user " + email + " sent successfully");

        } catch (IOException ex) {
            System.out.println("Error when trying to send email to:" + email);

            emailModel.setEmailFrom("brunofonseca821@gmail.com");
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailTo(email);
            emailModel.setSubject(subject);
            emailModel.setOwnerRef("Cadastro escolar");
            emailModel.setStatusEmail(StatusEmail.ERROR);
            throw ex;
        }finally {
            emailRepository.save(emailModel);
        }

    }

}
