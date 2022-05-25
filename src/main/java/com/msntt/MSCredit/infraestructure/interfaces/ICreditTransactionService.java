package com.msntt.MSCredit.infraestructure.interfaces;
import com.msntt.MSCredit.domain.dto.*;
import com.msntt.MSCredit.domain.model.CreditTransaction;
import reactor.core.publisher.Mono;

public interface ICreditTransactionService {
	
	public Mono<CreditTransaction> doCreditPayment (CreditPaymentDTO dto);
	public Mono<CreditTransaction> doCreditConsumption (CreditConsumptionDTO dto);
	public Mono<CreditTransaction> doCreditCardPayment (CreditCardPaymentDTO dto);
	public Mono<CreditTransaction> doCreditcardConsumption(CreditcardConsumptionDTO dto);
	public Mono<CreditTransaction> getNewCredit(CreateCreditDTO dto);
	
}
