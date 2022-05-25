package com.msntt.MSCredit.infraestructure.services;

import com.msntt.MSCredit.application.exception.CreditNotCreatedException;
import com.msntt.MSCredit.application.exception.EntityNotExistsException;
import com.msntt.MSCredit.application.exception.HasAccountException;
import com.msntt.MSCredit.domain.dto.BusinessPartnerDTO;
import com.msntt.MSCredit.domain.dto.CreateCreditCardDTO;
import com.msntt.MSCredit.domain.dto.CreateCreditDTO;
import com.msntt.MSCredit.domain.model.Credit;
import com.msntt.MSCredit.domain.repository.CreditRepository;
import com.msntt.MSCredit.infraestructure.interfaces.ICreditService;
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
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class CreditService implements ICreditService {
    //Servicios Crud de Credito
    @Autowired
    private CreditRepository repository;
    @Autowired
    private IBusinessPartnerClient businessPartnerClient;

    @Override
    public Flux<Credit> findAll() {

        return repository.findAll();
    }

    public Mono<Credit> savecompany(CreateCreditDTO a) {
        return Mono.just(a)
                .flatMap(existsBusinessPartner)
                .filter(r-> r.getCodeBusinessPartner().substring(0,1).equals("C"))
                .flatMap(saveCredit).switchIfEmpty(Mono.error(new CreditNotCreatedException()));
    }
    public Mono<Credit> save(CreateCreditDTO a) {

        return Mono.just(a)
                .flatMap(existsBusinessPartner)
                .filter(isPeople)
                .flatMap(countaccount)
                .filter(hasaccount)
                .switchIfEmpty(Mono.error(new HasAccountException()))
                .then(Mono.just(a))
                .flatMap(saveCredit).switchIfEmpty(Mono.error(new CreditNotCreatedException()));
    }
    @Override
    public Mono<Credit> delete(String Id) {

        return repository.findById(Id).flatMap(r -> repository.delete(r).then(Mono.just(r)));
    }

    @Override
    public Mono<Credit> findById(String Id) {
        // TODO Auto-generated method stub
        return repository.findById(Id);
    }
    public Mono<Long> countbybp(String id) {
        return repository.countBycodebusinesspartner(id);
    }

    public Mono<Credit> updateconsumption(String id, BigDecimal balance) {

        return repository.findById(id).flatMap(r -> {
                    r.setConsumedline(r.getConsumedline().add(balance));
                    r.setAvailableline(r.getAvailableline().subtract(balance));
                    return repository.save(r);
                }

        );
    }

    public Mono<Credit> updatepayments(String id, BigDecimal balance) {
        return repository.findById(id).flatMap(r -> {

                    r.setPaymentcredit(r.getPaymentcredit().add(balance));
                    return repository.save(r);
                }

        );
    }
    private final Function<CreateCreditDTO, Mono<Credit>> saveCredit = creditDto -> {

        Credit a;

        a = Credit.builder()

                .availableline(creditDto.getLimit())
                .consumedline(BigDecimal.valueOf(0.00))
                .paymentcredit(BigDecimal.valueOf(0.00))
                .approvedline(creditDto.getLimit())
                .codebusinesspartner(creditDto.getCodeBusinessPartner())
                .createdate(new Date()).build();

        return repository.save(a);

    };
    private final Function<CreateCreditDTO, Mono<CreateCreditDTO>> existsBusinessPartner = credit ->
            businessPartnerClient.findById(credit.getCodeBusinessPartner())
                    .flatMap(r -> Mono.just(credit));
    private final Function<CreateCreditDTO, Mono<Long>> countaccount = _long -> {

        return repository.countBycodebusinesspartner(_long.getCodeBusinessPartner());

    };
    private Predicate<Long> hasaccount= (a) ->
    {
        Long _a= 0L;
        Integer responsen;

        responsen = _a.compareTo(a);
        return responsen.equals(0);
    };
    private final Predicate<CreateCreditDTO> isPeople= (a) ->
    {

        return a.getCodeBusinessPartner().substring(0,1).equals("P");
    };
}
