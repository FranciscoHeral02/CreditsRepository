package com.msntt.MSCredit.infraestructure.interfaces;
import com.msntt.MSCredit.domain.dto.CreateCreditDTO;
import com.msntt.MSCredit.domain.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ICreditService {
    Flux<Credit> findAll();

    Mono<Credit> save(CreateCreditDTO _account);

    Mono<Credit> delete(String Id);

    Mono<Credit> findById(String Id);


}
