package com.udaipur.accounts.feign.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.udaipur.accounts.model.Cards;
import com.udaipur.accounts.model.Customer;
// this line added to check git commands for experiment only
@FeignClient("cards")
public interface CardsFeignClient {
	
	@RequestMapping(method=RequestMethod.POST, value="myCards", consumes="application/json")
	List<Cards>getCardDetails(@RequestHeader("my-bank-correlation-id") String correlationId, @RequestBody Customer customer);

}
