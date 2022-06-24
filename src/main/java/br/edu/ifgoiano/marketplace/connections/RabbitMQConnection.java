package br.edu.ifgoiano.marketplace.connections;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import br.edu.ifgoiano.marketplace.constants.RabbitMQConstants;

@Component
public class RabbitMQConnection {
	private static final String EXCHANGE_NAME = "amq.direct";
	private AmqpAdmin amqpAdmin;
	
	public RabbitMQConnection(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}
	
	private Queue queue(String queueName) {
		// Queue name, durable, exclusive, autoDelete
		return new Queue(queueName, true, false, false);
	}

	private DirectExchange directExchange() {
		return new DirectExchange(EXCHANGE_NAME);
	}

	private Binding binding(Queue queue, DirectExchange directExchange) {
		// destination, destination type, exchange, routing key, arguments
		return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(),
				null);
	}

	@PostConstruct
	private void addQueues() {
		Queue queueSendProducts  = this.queue(RabbitMQConstants.QUEUE_SEND_PRODUCTS);
		Queue queueReport        = this.queue(RabbitMQConstants.QUEUE_REPORT);
		Queue queueHeavyProducts = this.queue(RabbitMQConstants.QUEUE_HEAVY_PRODUCTS);
		Queue queueLightProducts = this.queue(RabbitMQConstants.QUEUE_LIGHT_PRODUCST);
	
		DirectExchange directExchange = this.directExchange();
		
		Binding bindingSendProducts  = this.binding(queueSendProducts, directExchange);
		Binding bindingReport        = this.binding(queueReport, directExchange);
		Binding bindingHeavyProducts = this.binding(queueHeavyProducts, directExchange);
		Binding bindingLightProducts = this.binding(queueLightProducts, directExchange);
		
		// Creating queues
		this.amqpAdmin.declareQueue(queueSendProducts);
		this.amqpAdmin.declareQueue(queueReport);
		this.amqpAdmin.declareQueue(queueHeavyProducts);
		this.amqpAdmin.declareQueue(queueLightProducts);
		
		// Creating exchange
		this.amqpAdmin.declareExchange(directExchange);
		
		// Creating bindings
		this.amqpAdmin.declareBinding(bindingSendProducts);
		this.amqpAdmin.declareBinding(bindingReport);
		this.amqpAdmin.declareBinding(bindingHeavyProducts);
		this.amqpAdmin.declareBinding(bindingLightProducts);
	}
	
}
