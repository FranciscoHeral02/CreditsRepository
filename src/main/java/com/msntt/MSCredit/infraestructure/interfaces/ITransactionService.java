package com.msntt.MSCredit.infraestructure.interfaces;

import com.msntt.MSCredit.domain.dto.CreditCardPaymentDTO;
import com.msntt.MSCredit.domain.dto.CreditConsumptionDTO;
import com.msntt.MSCredit.domain.dto.CreditPaymentDTO;
import com.msntt.MSCredit.domain.dto.CreditcardConsumptionDTO;
import com.msntt.MSCredit.domain.model.CreditCard;
import com.msntt.MSCredit.domain.model.CreditTransaction;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITransactionService {
	
	Flux<CreditTransaction> findAll();
	
	Mono<CreditTransaction> delete(String id);

	Mono<CreditTransaction> findById(String id);
	
	Mono<ResponseEntity<CreditTransaction>> update(String id, CreditTransaction request);
	
	Flux<CreditTransaction> saveAll(List<CreditTransaction> a);

	Flux<CreditTransaction> findBycardnumberAndcreditGreaterThan(String cardnumber);
	public Mono<CreditTransaction> doCreditPayment (CreditPaymentDTO dto);
	public Mono<CreditTransaction> doCreditConsumption (CreditConsumptionDTO dto);
	public Mono<CreditTransaction> doCreditCardPayment (CreditCardPaymentDTO dto);
	public Mono<CreditTransaction> doCreditcardConsumption(CreditcardConsumptionDTO dto);


}
