package com.example.pessoa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void enviaMensagem(String nomeFila, Object mensagem){
        try {
            String mensagemJSON = objectMapper.writeValueAsString(mensagem);
            this.rabbitTemplate.convertAndSend(nomeFila, mensagemJSON);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
