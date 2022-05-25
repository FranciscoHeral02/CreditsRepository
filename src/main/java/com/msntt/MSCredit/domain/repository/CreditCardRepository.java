package com.msntt.MSCredit.domain.repository;
import com.msntt.MSCredit.domain.model.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard, String> {
    Mono<CreditCard> findBycardNumber(String s);
    Mono<Long> countBycodeBusinessPartner(String s);
}
