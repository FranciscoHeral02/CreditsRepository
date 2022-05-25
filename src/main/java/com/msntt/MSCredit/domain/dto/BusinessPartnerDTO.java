package com.msntt.MSCredit.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class BusinessPartnerDTO {
	private String codeBP;
	private String docType;
	private String docNum;
	private String name;
	private String type;
	private String telephone1;
	private String telephone2;
	private String contactPerson;
	private BigDecimal creditCardLine;
	private BigDecimal creditLine;
	private BigDecimal creditCard;
	private BigDecimal debitLine;
	private BigDecimal debitCard;
	private String email;
	private Boolean valid;
}
