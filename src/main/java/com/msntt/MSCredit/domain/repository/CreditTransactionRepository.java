package com.msntt.MSCredit.domain.repository;
import com.msntt.MSCredit.domain.model.CreditTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
public interface CreditTransactionRepository extends ReactiveMongoRepository<CreditTransaction, String> {
}
