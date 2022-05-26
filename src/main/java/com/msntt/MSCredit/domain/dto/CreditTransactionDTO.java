/**
 * 
 */
package com.msntt.MSCredit.domain.dto;
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
public class CreditTransactionDTO {

    private TransactionType transactiontype;
    private Date createDate;
    @Digits(integer =20, fraction=6)
    private BigDecimal debit;
    @Digits(integer =20, fraction=6)
    private BigDecimal credit;
}
