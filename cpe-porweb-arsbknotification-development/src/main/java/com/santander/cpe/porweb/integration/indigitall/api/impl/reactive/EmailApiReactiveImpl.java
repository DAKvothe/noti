package com.santander.cpe.porweb.integration.indigitall.api.impl.reactive;

import com.santander.cpe.porweb.api.EmailApi;
import com.santander.cpe.porweb.integration.indigitall.service.IndigitallOutboundService;
import com.santander.cpe.porweb.integration.indigitall.service.IndigitallOutboundService.IndigitallException;
import com.santander.cpe.porweb.model.SendEmailRequest;
import com.santander.cpe.porweb.model.SendEmailResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class EmailApiReactiveImpl implements EmailApi {

    private final IndigitallOutboundService service;

    public EmailApiReactiveImpl(IndigitallOutboundService service) {
        this.service = service;
    }

    @Override
    @PostMapping(value = "/v2/email/send/list", consumes = "application/json", produces = "application/json")
    public Mono<ResponseEntity<SendEmailResponse>> sendEmailList(
            @RequestBody Mono<SendEmailRequest> request,
            ServerWebExchange exchange) {

        return request
            .flatMap(service::forwardEmail)
            .map(ResponseEntity::ok)
            .onErrorResume(IndigitallException.class, ex -> {
                SendEmailResponse err = new SendEmailResponse();
                try {
                    err.getClass().getMethod("setStatus", String.class).invoke(err, "error");
                    err.getClass().getMethod("setMessage", String.class).invoke(err, ex.getResponseBody());
                } catch (Exception ignore) {}
                return Mono.just(ResponseEntity.status(ex.getStatus().value()).body(err));
            });
    }
}
