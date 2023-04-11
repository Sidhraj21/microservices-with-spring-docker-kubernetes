package com.spring.udaipur.cards.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spring.udaipur.cards.config.CardsProperties;
import com.spring.udaipur.cards.config.CardsServiceConfig;
import com.spring.udaipur.cards.entity.Cards;
import com.spring.udaipur.cards.model.Customer;
import com.spring.udaipur.cards.repository.CardsRepository;

@RestController
public class CardsController {

	private static final Logger logger = LoggerFactory.getLogger(CardsController.class);
	
	@Autowired
	private CardsRepository cardsRepo;
	
	@Autowired
	private CardsServiceConfig cardsConfig;
	
	@PostMapping("/myCards/{id}")
	public List<Cards> getCardDetails (@PathVariable int id){
		
		List<Cards> cards =  cardsRepo.findByCustomerId(id);
		
		if(cards == null) {
			return null;
		}
		return cards;
	}
	
	@GetMapping(value="/cards/properties")
	public String getProperties() throws JsonProcessingException{
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		CardsProperties prop = new CardsProperties(cardsConfig.getMsg(), cardsConfig.getBuildVersion(),
				cardsConfig.getMailDetails(), cardsConfig.getActiveBranches());
		return ow.writeValueAsString(prop);
	}
	//this end point is created to be used by the FeignClient created in Accounts Controller and Feign interface
	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestHeader("my-bank-correlation-id") String correlationid,@RequestBody Customer customer) {
		logger.info("getCardDetails() method started");
		List<Cards> cards = cardsRepo.findByCustomerId(customer.getCustomerId());
		logger.info("getCardDetails() method ended");
		if (cards != null) {
			return cards;
		} else {
			return null;
		}

	}
}
