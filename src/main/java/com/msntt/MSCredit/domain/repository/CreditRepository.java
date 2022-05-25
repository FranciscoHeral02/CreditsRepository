package com.msntt.MSCredit.domain.repository;
import com.msntt.MSCredit.domain.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {

    public Mono<Long> countBycodebusinesspartner(String codebp);
}
