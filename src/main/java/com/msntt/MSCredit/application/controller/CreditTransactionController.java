package com.msntt.MSCredit.application.controller;

import com.msntt.MSCredit.domain.dto.CreditCardPaymentDTO;
import com.msntt.MSCredit.domain.dto.CreditConsumptionDTO;
import com.msntt.MSCredit.domain.dto.CreditPaymentDTO;
import com.msntt.MSCredit.domain.dto.CreditcardConsumptionDTO;
import com.msntt.MSCredit.domain.model.CreditTransaction;
import com.msntt.MSCredit.infraestructure.services.CreditService;
import com.msntt.MSCredit.infraestructure.services.CreditTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Credits/Services/Transaction")
public class CreditTransactionController {
    @Autowired
    private CreditTransactionService transactionservice;
    @PostMapping("/PaymentCredit")
    public Mono<ResponseEntity<Map<String, Object>>> PaymentCredit(@Valid @RequestBody Mono<CreditPaymentDTO> request) {

        Map<String, Object> response = new HashMap<>();

        return request.flatMap(a -> transactionservice.doCreditPayment(a)
                .map(c -> {
            response.put("Transactions",c);
            response.put("message", "Successful Withdrawal Transaction");
            return ResponseEntity.created(URI.create("/MsFundTransact/Entities/Transaction/".concat(c.getTransactionId())))
                    .contentType(MediaType.APPLICATION_JSON).body(response);
        })

        )







                ;
    }
    @PostMapping("/ConsumptionCredit")
    public Mono<ResponseEntity<Map<String, Object>>> ConsumptionCredit(@Valid @RequestBody Mono<CreditConsumptionDTO> request) {

        Map<String, Object> response = new HashMap<>();

        return request.flatMap(a -> transactionservice.doCreditConsumption(a)
                .map(c -> {
                    response.put("Transactions",c);
                    response.put("message", "Successful Withdrawal Transaction");
                    return ResponseEntity.created(URI.create("/MsFundTransact/Entities/Transaction/".concat(c.getTransactionId())))
                            .contentType(MediaType.APPLICATION_JSON).body(response);
                })

        )







                ;
    }
    @PostMapping("/PaymentCreditCards")
    public Mono<ResponseEntity<Map<String, Object>>> PaymentCreditCards(@Valid @RequestBody Mono<CreditCardPaymentDTO> request) {

        Map<String, Object> response = new HashMap<>();

        return request.flatMap(a -> transactionservice.doCreditCardPayment(a)
                .map(c -> {
                    response.put("Transactions",c);
                    response.put("message", "Successful Payment CreditCards Transaction");
                    return ResponseEntity.created(URI.create("/MsFundTransact/Entities/Transaction/".concat(c.getTransactionId())))
                            .contentType(MediaType.APPLICATION_JSON).body(response);
                })

        );
    }
    @PostMapping("/ConsumptionCreditCards")
    public Mono<ResponseEntity<Map<String, Object>>> ConsumptionCreditCards(@Valid @RequestBody Mono<CreditcardConsumptionDTO> request) {

        Map<String, Object> response = new HashMap<>();

        return request.flatMap(a -> transactionservice.doCreditcardConsumption(a)
                .map(c -> {
                    response.put("Transactions",c);
                    response.put("message", "Successful Consumption Credit Cards Transaction");
                    return ResponseEntity.created(URI.create("/MsFundTransact/Entities/Transaction/".concat(c.getTransactionId())))
                            .contentType(MediaType.APPLICATION_JSON).body(response);
                })

        )







                ;
    }
    @GetMapping("/Consumptions/CreditCard/{id}")
    public Flux<CreditTransaction> Consumptions(@PathVariable String id) {
        return transactionservice.findBycardnumberAndcreditGreaterThan(id);
    }
    @GetMapping("/Expireddebit/{id}")
    public Mono<Long> Expireddebit(@PathVariable String id) {
        return transactionservice.Expireddebit(id);
    }
}
