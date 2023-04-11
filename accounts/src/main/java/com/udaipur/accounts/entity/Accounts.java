package com.udaipur.accounts.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "accounts")
public class Accounts {

	@Column(name = "customer_id")
	private int customerId;
	
	@Id
	@Column(name = "account_number")
	private int accountNumber;
	
	@Column(name = "account_type")
	private String accountType;
	
	@Column(name = "branch_address")
	private String branchAddress;
	
	@Column(name = "create_dt")
	private LocalDate createDate;
	
	public Accounts(int customerId, String accountType, String branchAddress, LocalDate date) {
		this.customerId = customerId;
		this.accountType = accountType;
		this.branchAddress = branchAddress;
		this.createDate = date;
	}
	public Accounts() {};
}
