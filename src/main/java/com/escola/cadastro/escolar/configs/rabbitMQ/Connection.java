package com.escola.cadastro.escolar.configs.rabbitMQ;

import com.escola.cadastro.escolar.enums.Fila;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Connection {
    private AmqpAdmin amqpAdmin;

    public Connection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    public static final String NOME_EXCHANGE = "amq.direct";

    private Queue fila(String nomeFila){
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta(){
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona(){
        Queue filaCadatro =  this.fila(Fila.CADASTRO.toString());
        Queue filaNotas =  this.fila(Fila.NOTAS.toString());

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoEstoque = this.relacionamento(filaCadatro, troca);
        Binding ligacaoPreco = this.relacionamento(filaNotas, troca);

        this.amqpAdmin.declareQueue(filaCadatro);
        this.amqpAdmin.declareQueue(filaNotas);

        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(ligacaoEstoque);
        this.amqpAdmin.declareBinding(ligacaoPreco);

    }
}
