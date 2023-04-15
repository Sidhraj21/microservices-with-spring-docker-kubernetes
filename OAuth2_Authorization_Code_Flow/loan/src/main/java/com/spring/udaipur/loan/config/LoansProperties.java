package com.spring.udaipur.loan.config;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class LoansProperties {

	private String msg;
	private String buildVersion;
	private Map<String, String>mailDetails;
	private List<String> loanType;
	
	public LoansProperties(String msg, String buildVersion, Map<String, String>map, List<String>loanType){
		{
			this.msg = msg;
			this.buildVersion = buildVersion;
			this.mailDetails = map;
			this.loanType = loanType;
	}
		
	}
}
