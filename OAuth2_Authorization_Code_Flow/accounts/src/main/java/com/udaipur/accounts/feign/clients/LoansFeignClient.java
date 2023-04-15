package com.udaipur.accounts.feign.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.udaipur.accounts.model.Customer;
import com.udaipur.accounts.model.Loans;

@FeignClient("loans")
public interface LoansFeignClient {
	
	@RequestMapping(method=RequestMethod.POST, value="myLoans", consumes="application/json")
	List<Loans>getLoanDetails(@RequestHeader("my-bank-correlation-id") String correlationId, @RequestBody Customer customer);

}