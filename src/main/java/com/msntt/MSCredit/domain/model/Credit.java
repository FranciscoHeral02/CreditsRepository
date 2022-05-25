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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Credit {
    @Id
    @JsonIgnore
    private String creditid;
    @NotNull
    private String codebusinesspartner;
    @NotNull
    private String cardNumber;
    @NotNull
    private String credittype;
    @JsonIgnore
    private Date createdate;
    @Digits(integer = 20, fraction = 6)
    private BigDecimal approvedline;
    @Digits(integer = 20, fraction = 6)
    private BigDecimal availableline;
    @Digits(integer = 20, fraction = 6)
    private BigDecimal consumedline;
    @Digits(integer = 19,fraction = 6)
    private BigDecimal paymentcredit;
    @Digits(integer = 19,fraction = 6)
    private BigDecimal expireddebt;

}
