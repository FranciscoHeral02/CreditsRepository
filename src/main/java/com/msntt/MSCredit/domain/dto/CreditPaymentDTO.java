package com.msntt.MSCredit.domain.dto;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditPaymentDTO {
    @NotBlank
    private String creditid;
    @NotNull
    private String codebusinesspartner;
    @NotNull
    @Digits(integer =20, fraction=6)
    private BigDecimal payment;
}
