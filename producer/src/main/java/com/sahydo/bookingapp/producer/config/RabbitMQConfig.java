package com.sahydo.bookingapp.producer.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sahydo.bookingapp.producer.utilities.BookingRequestsConstants;

@Configuration
public class RabbitMQConfig {

	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange(BookingRequestsConstants.DEAD_LETTER_EXCHANGE_NAME);
	}

	@Bean
	Queue dlq() {
		return QueueBuilder.durable("deadLetterQueue").build();
	}

	@Bean
	Queue queue() {
		return QueueBuilder.durable("bookingrequest.queue").withArgument("x-dead-letter-exchange",  BookingRequestsConstants.DEAD_LETTER_EXCHANGE_NAME)
				.withArgument("x-dead-letter-routing-key", BookingRequestsConstants.DEAD_LETTER_ROUTING_KEY_NAME).build();
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(BookingRequestsConstants.DIRECT_EXCHANGE_NAME);
	}

	@Bean
	Binding DLQbinding(Queue dlq, DirectExchange deadLetterExchange) {
		return BindingBuilder.bind(dlq).to(deadLetterExchange).with(BookingRequestsConstants.DEAD_LETTER_ROUTING_KEY_NAME);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(BookingRequestsConstants.QUEUE_NAME);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
