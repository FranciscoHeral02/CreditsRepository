package com.msntt.MSCredit.domain.repository;
import com.msntt.MSCredit.domain.dto.CreditTransactionDTO;
import com.msntt.MSCredit.domain.model.CreditTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public interface CreditTransactionRepository extends ReactiveMongoRepository<CreditTransaction, String> {
    Flux<CreditTransaction> findBycreditcardAndDebitGreaterThan(String cardnumber,BigDecimal int_);
   // Flux<CreditTransactionDTO> findBycreditcardAndcodebusinesspartner(String cardnumber, String CodeBP);
}
