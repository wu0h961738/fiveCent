package com.venom.backend.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitRecieve {

    @RabbitListener(queues = "queue_pool")
    public void receive1(String in){
        System.out.println("pool listener recieved msg : " + in);
    }

    @RabbitListener(queues = "queue_activity")
    public void receive2(String in){
        System.out.println("activity listener recieved msg : " + in);
    }
}
