package br.edu.ifgoiano.marketplace.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RabbitMQService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public void sendMessage(String queueName, Object message) {
		try {
			String jsonMessage = this.objectMapper.writeValueAsString(message);	
			this.rabbitTemplate.convertAndSend(queueName, jsonMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
