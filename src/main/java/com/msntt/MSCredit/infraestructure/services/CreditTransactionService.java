package com.msntt.MSCredit.infraestructure.services;

import com.msntt.MSCredit.application.exception.EntityNotExistsException;
import com.msntt.MSCredit.domain.constant.TransactionType;
import com.msntt.MSCredit.domain.dto.*;
import com.msntt.MSCredit.domain.model.Credit;
import com.msntt.MSCredit.domain.model.CreditCard;
import com.msntt.MSCredit.domain.model.CreditTransaction;
import com.msntt.MSCredit.domain.repository.CreditCardRepository;
import com.msntt.MSCredit.domain.repository.CreditRepository;
import com.msntt.MSCredit.domain.repository.CreditTransactionRepository;
import com.msntt.MSCredit.infraestructure.interfaces.ITransactionService;
import com.msntt.MSCredit.infraestructure.restclient.IBusinessPartnerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

@Service
public class CreditTransactionService implements ITransactionService {

    //Attribute
    @Autowired
    CreditTransactionRepository trepository;

    @Autowired
    CreditCardRepository crepository;
    @Autowired
    CreditRepository crrepository;
    @Autowired
    private IBusinessPartnerClient businessPartnerClient;
    //Crud
    @Override
    public Flux<CreditTransaction> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<CreditTransaction> delete(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<CreditTransaction> findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<ResponseEntity<CreditTransaction>> update(String id, CreditTransaction request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Flux<CreditTransaction> saveAll(List<CreditTransaction> a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Flux<CreditTransaction> findBycardnumberAndcreditGreaterThan(String cardnumber) {
        return trepository.findBycreditcardAndDebitGreaterThan(cardnumber ,BigDecimal.ZERO);
    }

    //Transaction

    // Credits Transactions
    @Override
    public Mono<CreditTransaction> doCreditPayment(CreditPaymentDTO dto) {

        return crrepository.findById(dto.getCreditid())
                .filter(r -> isValidPaymentCredit.test(r, dto))
                .then(Mono.just(dto))
                .flatMap(updatepaymentscredit)
                .switchIfEmpty(Mono.error(new EntityNotExistsException()))
                .then(Mono.just(dto))
                .flatMap(savepaymentcredit);
    }

    @Override
    public Mono<CreditTransaction> doCreditConsumption(CreditConsumptionDTO dto) {
        return crrepository.findById(dto.getCreditid())
                .filter(r -> isValidConsumptionCredit.test(r, dto))
                .then(Mono.just(dto))
                .flatMap(updateconsumptioncredit)
                .switchIfEmpty(Mono.error(new EntityNotExistsException()))
                .then(Mono.just(dto))
                .flatMap(saveconsumptioncredit);
    }


    @Override
    public Mono<CreditTransaction> doCreditCardPayment(CreditCardPaymentDTO dto) {
        return crepository.findBycardNumber(dto.getCreditcard())
                .filter(r -> isValidPaymentCreditCard.test(r, dto))
                .then(Mono.just(dto))
                .flatMap(updatepaymentcreditcard)
                .switchIfEmpty(Mono.error(new EntityNotExistsException()))
                .then(Mono.just(dto))
                .flatMap(Savepaymentcreditcard);
    }

    @Override
    public Mono<CreditTransaction> doCreditcardConsumption(CreditcardConsumptionDTO dto) {
        return  crepository.findBycardNumber(dto.getCreditcard())
                .filter(r -> isValidConsumptionCreditCard.test(r, dto))
                .then(Mono.just(dto))
                .flatMap(updateconsumptioncreditcard)
                .switchIfEmpty(Mono.error(new EntityNotExistsException()))
                .then(Mono.just(dto))
                .flatMap(saveconsumptioncreditcard);
    }

    public Mono<Long> Expireddebit(String Businesspartner)
    {
        Mono<Long> QtyCredit = crrepository.countBycodebusinesspartnerAndExpireddebtGreaterThan(Businesspartner, BigDecimal.ZERO);
        Mono<Long> QtyCreditCard = crepository.countBycodeBusinessPartnerAndExpireddebtGreaterThan(Businesspartner, BigDecimal.ZERO);
        Mono<Long> Result = Mono.zip(QtyCredit, QtyCreditCard,(a, b) -> a + b);
        return Result;
    }

    //Methods
    private final BiPredicate<CreditCard, CreditcardConsumptionDTO> isValidConsumptionCreditCard = (a, b) ->
    {
        BigDecimal _a, _b;
        Integer responsen;
        _a = a.getAvailableline();
        _b = a.getConsumedline().add(b.getConsumption());
        responsen = _a.compareTo(_b);
        return responsen >= 0;
    };
    private final BiPredicate<Credit, CreditConsumptionDTO> isValidConsumptionCredit = (a, b) ->
    {
        BigDecimal _a, _b;
        Integer responsen;
        _a = a.getAvailableline();
        _b = a.getConsumedline().add(b.getConsumption());
        responsen = _a.compareTo(_b);
        return responsen >= 0;
    };
    private final BiPredicate<CreditCard, CreditCardPaymentDTO> isValidPaymentCreditCard = (a, b) ->
    {
        BigDecimal _a, _b;
        Integer responsen;
        _a = a.getConsumedline();
        _b = b.getPayment();
        responsen = _a.compareTo(_b);
        return responsen >= 0;
    };
    private final BiPredicate<Credit, CreditPaymentDTO> isValidPaymentCredit = (a, b) ->
    {
        BigDecimal _a, _b;
        Integer responsen;

        _a = a.getConsumedline();
        _b = b.getPayment();
        responsen = _a.compareTo(_b);
        return  responsen >= 0;
    };

    public Function<CreditConsumptionDTO, Mono<Credit>> updateconsumptioncredit = consumption -> {

        return crrepository.findById(consumption.getCreditid()).flatMap(r -> {
                    r.setConsumedline(r.getConsumedline().add(consumption.getConsumption()));
                    r.setAvailableline(r.getAvailableline().subtract(consumption.getConsumption()));
                    return crrepository.save(r);
                }

        );
    };

    public  Function<CreditPaymentDTO, Mono<Credit>> updatepaymentscredit= consumption -> {
        return crrepository.findById(consumption.getCreditid()).flatMap(r -> {

                    r.setPaymentcredit(r.getPaymentcredit().add(consumption.getPayment()));
                    return crrepository.save(r);
                }

        );
    };

    public  Function<CreditcardConsumptionDTO, Mono<CreditCard>>  updateconsumptioncreditcard = consumption -> {

        return crepository.findBycardNumber(consumption.getCreditcard()).flatMap(r -> {
                    r.setConsumedline(r.getConsumedline().add(consumption.getConsumption()));
                    r.setAvailableline(r.getAvailableline().subtract(consumption.getConsumption()));
                    return crepository.save(r);
                }

        );
    };

    public  Function<CreditCardPaymentDTO, Mono<CreditCard>>  updatepaymentcreditcard= consumption -> {
        return crepository.findBycardNumber(consumption.getCreditcard()).flatMap(r -> {
            r.setConsumedline(r.getConsumedline().subtract(consumption.getPayment()));
            r.setAvailableline(r.getAvailableline().add(consumption.getPayment()));
            return crepository.save(r);
    });
    };
    private final Function<CreateCreditDTO, Mono<CreateCreditDTO>> existsBusinessPartner = credit ->
            businessPartnerClient.findById(credit.getCodeBusinessPartner())
                    .flatMap(r -> Mono.just(credit));

    private final Function<CreditcardConsumptionDTO, Mono<CreditTransaction>> saveconsumptioncreditcard = consumption -> {

        CreditTransaction t;
        Mono<CreditTransaction> _t;
        t = CreditTransaction.builder()
                .debit(consumption.getConsumption())
                .creditcard(consumption.getCreditcard())
                .transactiontype(TransactionType.CREDIT_CARD_CONSUMPTION)
                .createDate(new Date()).build();
        _t = trepository.save(t);
        return _t;
    };
    private final Function<CreditCardPaymentDTO, Mono<CreditTransaction>> Savepaymentcreditcard = payment -> {

        CreditTransaction t;
        Mono<CreditTransaction> _t;
        t = CreditTransaction.builder()
                .credit(payment.getPayment())
                .creditcard(payment.getCreditcard())
                .transactiontype(TransactionType.CREDIT_CARD_PAYMENT)
                .createDate(new Date()).build();
        _t = trepository.save(t);
        return _t;
    };
    private final Function<CreditPaymentDTO, Mono<CreditTransaction>> savepaymentcredit = payment -> {

        CreditTransaction t;
        Mono<CreditTransaction> _t;
        t = CreditTransaction.builder()
                .credit(payment.getPayment())
                .creditid(payment.getCreditid())
                .transactiontype(TransactionType.CREDIT_PAYMENT)
                .createDate(new Date()).build();
        _t = trepository.save(t);
        return _t;
    };
    private final Function<CreditConsumptionDTO, Mono<CreditTransaction>> saveconsumptioncredit = consumption -> {

        CreditTransaction t;
        Mono<CreditTransaction> _t;
        t = CreditTransaction.builder()
                .debit(consumption.getConsumption())
                .creditid(consumption.getCreditid())
                .transactiontype(TransactionType.CREDIT_CONSUMPTION)
                .createDate(new Date()).build();
        _t = trepository.save(t);
        return _t;
    };

}






