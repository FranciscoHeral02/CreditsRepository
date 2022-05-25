package com.msntt.MSCredit.infraestructure.restclient;

import com.msntt.MSCredit.domain.dto.BusinessPartnerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(value = "bp-service", url = "${bp.service.url}")
public interface IBusinessPartnerClient {
    @GetMapping("/{id}")
    public Mono<BusinessPartnerDTO> findById(@PathVariable("id") String id);
}

