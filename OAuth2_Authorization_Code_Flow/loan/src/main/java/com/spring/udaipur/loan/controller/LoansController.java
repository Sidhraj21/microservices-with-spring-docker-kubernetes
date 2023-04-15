package com.spring.udaipur.loan.controller;

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
import com.spring.udaipur.loan.config.LoanConfigServer;
import com.spring.udaipur.loan.config.LoansProperties;
import com.spring.udaipur.loan.entity.Loans;
import com.spring.udaipur.loan.model.Customer;
import com.spring.udaipur.loan.repository.LoanRepository;

@RestController
public class LoansController {

	private static final Logger logger = LoggerFactory.getLogger(LoansController.class);
	
	@Autowired 
	private LoanRepository loanRepo;
	
	@Autowired
	private LoanConfigServer loanConfig;
	
	@PostMapping("/myLoans/{id}")
	public List<Loans> getLoansDetails (@PathVariable int id) {
		
		List<Loans> loans = loanRepo.findByCustomerIdOrderByStartDtDesc(id);
		
		if(loans == null) {
			return null;
		}
		return loans;
	}
	
	@GetMapping(value="/loans/properties")
	public String getProperties() throws JsonProcessingException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		LoansProperties prop = new LoansProperties(loanConfig.getMsg(),loanConfig.getBuildVersion(),
				loanConfig.getMailDetails(), loanConfig.getLoanType());
		return ow.writeValueAsString(prop);
	}
	
	@PostMapping(value="/myLoans")
	public List<Loans> getLoansDetails(@RequestHeader("my-bank-correlation-id") String correlationid,@RequestBody Customer customer) {
		logger.info("getLoansDetails() method started");
		List<Loans> loans = loanRepo.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		logger.info("getLoansDetails() method ended");
		if (loans != null) {
			return loans;
		} else {
			return null;
		}

	}
}
