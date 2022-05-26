package com.msntt.MSCredit.domain.repository;
import com.msntt.MSCredit.domain.dto.CreditCardDTO;
import com.msntt.MSCredit.domain.model.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
@Repository
public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard, String> {
    Mono<CreditCard> findBycardNumber(String s);
    Mono<Long> countBycodeBusinessPartner(String s);
    Mono<Long> countBycodeBusinessPartnerAndExpireddebtGreaterThan(String s, BigDecimal _bd);
    Flux<CreditCardDTO>findBycodeBusinessPartner(String s);
}
