package com.udaipur.accounts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.udaipur.accounts.config.AccountProperties;
import com.udaipur.accounts.config.AccountsServiceConfig;
import com.udaipur.accounts.entity.Accounts;
import com.udaipur.accounts.feign.clients.CardsFeignClient;
import com.udaipur.accounts.feign.clients.LoansFeignClient;
import com.udaipur.accounts.model.Cards;
import com.udaipur.accounts.model.Customer;
import com.udaipur.accounts.model.CustomerDetails;
import com.udaipur.accounts.model.Loans;
import com.udaipur.accounts.repository.AccountsRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;

@RestController
public class AccountController {

	@Autowired
	private AccountsRepository accountRepository;
	
	@Autowired
	private AccountsServiceConfig accountConfig;
	
	@Autowired
	private LoansFeignClient loanFeignClient;
	
	@Autowired
	private CardsFeignClient cardsFeignClient;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	//Later change this id to Customer Class
	@GetMapping(value="/getAccount/{id}")
	@Timed(value="getAccountDetails.time", description="Time taken to return Account By Id")
	@ResponseBody
	public Accounts getAccountById(@PathVariable int id) {
		System.out.println("The id is ::: " + id);
		Accounts account = accountRepository.findByAccountNumber(id);
		System.out.println("Account number is :::: " + account.getAccountNumber());
		System.out.println("Account type is :::: " + account.getAccountType());
		System.out.println("Account address is :::: " + account.getBranchAddress());
		
		if(account == null) {
			System.out.println("account is null");
			
		}
		return account;
		
	}
	
	@GetMapping(value="/account/properties")
	public String getProperties() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		AccountProperties prop = new AccountProperties(accountConfig.getMsg(), accountConfig.getBuildVersion(),
				accountConfig.getMailDetails(), accountConfig.getActiveBranches());
		return ow.writeValueAsString(prop);
	}
	
	@PostMapping(value="/myCustomerDetails")
	@CircuitBreaker(name="detailsForCustomerSupportApp", fallbackMethod="myCustomerDetailsFallBack")
	@Retry(name="detailsForCustomerSupportApp")
	public CustomerDetails myCustomerDetails(@RequestHeader("my-bank-correlation-id") String correlationId, @RequestBody Customer customer) {
		logger.info("myCustomerDetailsMethod() started");
		Accounts accounts = accountRepository.findByAccountNumber(customer.getCustomerId());
		List<Loans>loans = loanFeignClient.getLoanDetails(correlationId, customer);
		List<Cards>cards = cardsFeignClient.getCardDetails(correlationId, customer);
		
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setLoans(loans);
		customerDetails.setCards(cards);
		logger.info("myCustomerDetails() method ended");
		return customerDetails;
	}

	private CustomerDetails myCustomerDetailsFallBack(@RequestHeader("my-bank-correlation-id") String correlationId, Customer customer, Throwable t) {
		Accounts accounts = accountRepository.findByAccountNumber(customer.getCustomerId());
		List<Loans> loans = loanFeignClient.getLoanDetails(correlationId, customer);
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setLoans(loans);
		return customerDetails;

	}
	
	@GetMapping(value="/sayHello")
	@RateLimiter(name="sayHello", fallbackMethod="sayHelloFallBack")
	public String sayHello() {
		logger.info("sayHello Method() started");
		logger.info("sayHello Method() ended");
		return "Hello, Welcome to my bank app inside Kubernetes";
		
	}
	
	private String sayHelloFallBack(Throwable t) {
		return "Hi, Welcome to Fallback Method";
	}
}
