package com.msntt.MSCredit.domain.dto;

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
public class CreditCardDTO {
	@Id
	private String cardNumber;
	@Digits(integer = 20, fraction = 6)
	private BigDecimal availableline;

}
