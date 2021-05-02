package com.venom.backend.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue_pool(){
        return new Queue("queue_pool");
    }

    @Bean
    public Queue queue_activity(){
        return new Queue("queue_activity");
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange("topicExchanger");
    }

    @Bean
    Binding bindToPool(Queue queue_pool, TopicExchange exchange){ //很神奇 參數會自動在IoC裡面找跟param相同的Bean Name
        return BindingBuilder.bind(queue_pool).to(exchange).with("pool.#");
    }

    @Bean
    Binding bindToActivity(Queue queue_activity, TopicExchange exchange){
        return BindingBuilder.bind(queue_activity).to(exchange).with("activity.#");
    }
}
