package com.venom.backend.service;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.QueueParser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service(value="Rabbit")
public class RabbitSvc {

    @Autowired
    private RabbitTemplate sendTempl;

    @Autowired
    private TopicExchange exchanger;

    public void sendToExch(Map<String,Object> reqMap){


        //TODO how to dynamic binding? (Currently, statically bind in ConfigTest.)
//        String clientCode = (String) reqMap.get("clientCode");
//        if(mockQSet.containsKey(clientCode)){
//            BindingBuilder.bind(mockQSet.get(clientCode)).to(clientFan);
//        }else{
//            Queue newQ = new Queue(clientCode+"Queue");
//            BindingBuilder.bind(newQ).to(clientFan);
//            mockQSet.put(clientCode, newQ);
//        }
        sendToPool(reqMap.get("ticketCode").toString());
        sendToActivity("some pool are forwarding to full.");
    }

    private void sendToPool(String ticketCode){
        this.sendTempl.convertAndSend(exchanger.getName(),"pool.#", ticketCode);
        System.out.println(ticketCode + " was added to pool");
    }

    private void sendToActivity(String msg){
        this.sendTempl.convertAndSend(exchanger.getName(),"activity.#", msg);
        System.out.println(msg);
    }
}
