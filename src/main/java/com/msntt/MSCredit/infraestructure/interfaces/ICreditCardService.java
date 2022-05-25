package com.msntt.MSCredit.infraestructure.interfaces;

import com.msntt.MSCredit.domain.dto.CreateCreditCardDTO;
import com.msntt.MSCredit.domain.model.CreditCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ICreditCardService {
    Flux<CreditCard> findAll();

    Mono<CreditCard> save(CreditCard _entity);

    Mono<CreditCard> createCreditCard(CreateCreditCardDTO _entity);

    Mono<CreditCard> delete(String Id);

    Mono<CreditCard> findById(String Id);

}
