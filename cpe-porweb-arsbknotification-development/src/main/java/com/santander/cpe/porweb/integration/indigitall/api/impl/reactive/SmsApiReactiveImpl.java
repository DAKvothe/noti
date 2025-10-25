package com.santander.cpe.porweb.integration.indigitall.api.impl.reactive;

import com.santander.cpe.porweb.api.SmsApi;
import com.santander.cpe.porweb.integration.indigitall.service.IndigitallOutboundService;
import com.santander.cpe.porweb.integration.indigitall.service.IndigitallOutboundService.IndigitallException;
import com.santander.cpe.porweb.model.SmsSendListRequest;
import com.santander.cpe.porweb.model.SmsSendResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class SmsApiReactiveImpl implements SmsApi {

    private final IndigitallOutboundService service;

    public SmsApiReactiveImpl(IndigitallOutboundService service) {
        this.service = service;
    }

    @Override
    @PostMapping(value = "/v2/sms/send/list", consumes = "application/json", produces = "application/json")
    public Mono<ResponseEntity<SmsSendResponse>> sendSmsList(
            @RequestBody Mono<SmsSendListRequest> request,
            ServerWebExchange exchange) {

        return request
            .flatMap(service::forwardSms)
            .map(ResponseEntity::ok)
            .onErrorResume(IndigitallException.class, ex -> {
                SmsSendResponse err = new SmsSendResponse();
                try {
                    err.getClass().getMethod("setStatus", String.class).invoke(err, "error");
                    err.getClass().getMethod("setMessage", String.class).invoke(err, ex.getResponseBody());
                } catch (Exception ignore) {}
                return Mono.just(ResponseEntity.status(ex.getStatus().value()).body(err));
            });
    }
}
