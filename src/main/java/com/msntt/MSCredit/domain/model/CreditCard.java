package com.msntt.MSCredit.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class CreditCard {
	@Id
	private String cardNumber;
	@NotNull
	private String cardName;
	private String expiringDate;
	@Digits(integer = 20, fraction = 6)
	private BigDecimal approvedline;
	@Digits(integer = 20, fraction = 6)
	private BigDecimal availableline;
	@Digits(integer = 20, fraction = 6)
	private BigDecimal consumedline;
	@Digits(integer = 19,fraction = 6)
	private BigDecimal expireddebt;
	@NotNull
	private String cvv;
	@NotNull
	private Boolean valid;
	@NotNull
	private Date createdate;
	@NotNull
	private String codeBusinessPartner;
}
