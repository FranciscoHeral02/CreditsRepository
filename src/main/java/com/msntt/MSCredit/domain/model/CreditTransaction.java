/**
 * 
 */
package com.msntt.MSCredit.domain.model;
import com.msntt.MSCredit.domain.constant.TransactionType;
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
public class CreditTransaction {
	
	@Id
    private String transactionId;
    @NotNull
    private TransactionType transactiontype;
    private String fromaccount;
    private String toaccount;
    private Date createDate;
    @NotNull
    @Digits(integer =20, fraction=6)
    private BigDecimal amount;
    @Digits(integer =20, fraction=6)
    private BigDecimal debit;
    @Digits(integer =20, fraction=6)
    private BigDecimal credit;
    private String creditcard;
    private String creditid;

	
}
