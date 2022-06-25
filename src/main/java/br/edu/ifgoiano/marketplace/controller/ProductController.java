package br.edu.ifgoiano.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constants.RabbitMQConstants;
import dto.ProductDto;
import br.edu.ifgoiano.marketplace.service.RabbitMQService;

@RestController
@RequestMapping(value = "products")
public class ProductController {

	@Autowired
	private RabbitMQService rabbitMQService;

	@PostMapping
	private ResponseEntity createProduct(@RequestBody ProductDto productDto) {
		this.rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_SEND_PRODUCTS, productDto);
		System.out.println(productDto.id + ", " + productDto.name + ", " + productDto.sender + ", "
				+ productDto.receiver + ", " + productDto.weight);
		return new ResponseEntity(HttpStatus.OK);
	}

}
