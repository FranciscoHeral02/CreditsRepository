package com.msntt.MSCredit.infraestructure.services;

import com.msntt.MSCredit.application.exception.CreditNotCreatedException;
import com.msntt.MSCredit.application.exception.EntityNotExistsException;
import com.msntt.MSCredit.application.helpers.CardGeneratorValues;
import com.msntt.MSCredit.domain.dto.BusinessPartnerDTO;
import com.msntt.MSCredit.domain.dto.CreateCreditCardDTO;
import com.msntt.MSCredit.domain.dto.CreditCardDTO;
import com.msntt.MSCredit.domain.dto.CreditcardConsumptionDTO;
import com.msntt.MSCredit.domain.model.CreditCard;
import com.msntt.MSCredit.domain.repository.CreditCardRepository;
import com.msntt.MSCredit.infraestructure.interfaces.ICreditCardService;
import com.msntt.MSCredit.infraestructure.restclient.IBusinessPartnerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class CreditCardService implements ICreditCardService {
    //Servicios Crud de Tarjetas de Credito
    @Autowired
    CreditCardRepository repository;

    @Autowired
    private IBusinessPartnerClient businessPartnerClient;

    @Override
    public Flux<CreditCard> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<CreditCard> save(CreditCard _entity) {
        return null;
    }

    @Override
    public Mono<CreditCard> createCreditCard(CreateCreditCardDTO _entity) {
        return Mono.just(_entity)
                .flatMap(existsBusinessPartner)
                .flatMap(saveCreditCard).switchIfEmpty(Mono.error(new CreditNotCreatedException()));
    }

    @Override
    public Mono<CreditCard> delete(String Id) {
        return repository.findById(Id).flatMap(deleted -> repository.delete(deleted).then(Mono.just(deleted)))
                .switchIfEmpty(Mono.error(new EntityNotExistsException()));
    }

    @Override
    public Mono<CreditCard> findById(String Id) {
        return repository.findById(Id);
    }
    public Mono<Long> countbycreditcard(String id) {
        return repository.countBycodeBusinessPartner(id);
    }


    public Flux<CreditCardDTO> availableamountcreditcard(String id) {
        return repository.findBycodeBusinessPartner(id);
    }



    public Mono<Long> countbycreditcardAndexpireddebit(String id) {
        return repository.countBycodeBusinessPartnerAndExpireddebtGreaterThan(id, BigDecimal.ZERO);
    }
    public Mono<CreditCard> updateconsumption(String id, BigDecimal balance) {

        return repository.findById(id).flatMap(r -> {
                    r.setConsumedline(r.getConsumedline().add(balance));
                    r.setAvailableline(r.getAvailableline().subtract(balance));
                    return repository.save(r);
                }

        );
    }

    public Mono<CreditCard> updatepayments(String id, BigDecimal balance) {
        return repository.findById(id).flatMap(a ->
        {
            a.setConsumedline(a.getConsumedline().subtract(balance));
            a.setAvailableline(a.getAvailableline().add(balance));
            return repository.save(a);
        });
    }

    private final Consumer<String> getBusinessPartner = businessPartnerId -> {
        businessPartnerClient.findById(businessPartnerId)
                .flatMap(r -> Mono.just(businessPartnerId)).switchIfEmpty(Mono.error(CreditNotCreatedException::new));

    };
    private final Function<CreateCreditCardDTO, Mono<CreateCreditCardDTO>> existsBusinessPartner = (card) ->
            businessPartnerClient.findById(card.getCodeBusinessPartner())
             .flatMap(r -> Mono.just(card));

    private final Function<CreateCreditCardDTO, Mono<CreditCard>> saveCreditCard = creditCardDto -> {

        CreditCard a;

        a = CreditCard.builder()
                .cardNumber(CardGeneratorValues.CardNumberGenerate())
                .approvedline(creditCardDto.getLimit())
                .availableline(creditCardDto.getLimit())
                .consumedline(BigDecimal.ZERO)
                .valid(true)
                .expiringDate(CardGeneratorValues.CardExpiringDateGenerate())
                .codeBusinessPartner(creditCardDto.getCodeBusinessPartner())
                .cvv(CardGeneratorValues.CardCVVGenerate())
                .expireddebt(BigDecimal.ZERO)
                .createdate(new Date()).build();


        return repository.save(a);

    };
    private final Function<CreditcardConsumptionDTO, Mono<CreditCard>> updateconsumptionCreditCard = creditCardDto -> {

        CreditCard a;

        a = CreditCard.builder()
                .cardNumber(creditCardDto.getCreditcard())
                .consumedline(creditCardDto.getConsumption())
                .build();

        return repository.save(a);

    };

}
