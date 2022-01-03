package com.sahydo.bookingapp.consumer;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ErrorHandler;

@SpringBootApplication
public class BookingAppExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingAppExchangeApplication.class, args);
	}

	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(converter());
		factory.setErrorHandler(errorHandler());
		return factory;
	}

	@Bean
	public ErrorHandler errorHandler() {
		return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
	}

	public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

		@Override
		public boolean isFatal(Throwable t) {
			return super.isFatal(t);
		}

	}
}
