package br.edu.ifgoiano.marketplace.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import constants.RabbitMQConstants;
import dto.ReportDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReportConsumer {

	@RabbitListener(queues = RabbitMQConstants.QUEUE_REPORT)
	private void consumeReport(String message) throws JsonMappingException, JsonProcessingException {
		ReportDto reportDto = new ObjectMapper().readValue(message, ReportDto.class);

		System.out.println(reportDto.id + ", " + reportDto.productId + ", " + reportDto.enterprise + ", "
				+ reportDto.deliveryDate);
	}

}
