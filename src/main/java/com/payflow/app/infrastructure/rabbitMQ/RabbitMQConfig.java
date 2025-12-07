package com.payflow.app.infrastructure.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    public static final String BILLING_EXCHANGE = "billing-exchange";
    public static final String BILLING_QUEUE = "billing-queue";
    public static final String BILLING_ROUTING_KEY = "billing-routing-key";

    @Bean
    public Queue billingQueue() {
        return new Queue(BILLING_QUEUE, true);
    }

    @Bean
    public DirectExchange billingExchange() {
        return new DirectExchange(BILLING_EXCHANGE);
    }

    @Bean
    public Binding billingBinding(Queue billingQueue, DirectExchange billingExchange) {
        return BindingBuilder.bind(billingQueue)
                .to(billingExchange)
                .with(BILLING_ROUTING_KEY);
    }
}
