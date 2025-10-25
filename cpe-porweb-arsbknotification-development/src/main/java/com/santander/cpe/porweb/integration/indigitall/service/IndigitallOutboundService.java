package com.santander.cpe.porweb.integration.indigitall.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.santander.cpe.porweb.model.SendEmailRequest;
import com.santander.cpe.porweb.model.SendEmailResponse;
import com.santander.cpe.porweb.model.SmsSendListRequest;
import com.santander.cpe.porweb.model.SmsSendResponse;

@Service
public class IndigitallOutboundService {

    private final WebClient client;

    public IndigitallOutboundService(WebClient indigitallWebClient) {
        this.client = indigitallWebClient;
    }

    public Mono<SendEmailResponse> forwardEmail(SendEmailRequest req) {
        return client.post()
            .uri("/v2/email/send/list")
            .bodyValue(req)
            .exchangeToMono(resp -> {
                if (resp.statusCode().is2xxSuccessful()) {
                    return resp.bodyToMono(SendEmailResponse.class);
                }
                return resp.bodyToMono(String.class)
                    .defaultIfEmpty("")
                    .flatMap(body -> Mono.error(new IndigitallException(HttpStatusCode.valueOf(resp.rawStatusCode()), body)));
            });
    }

    public Mono<SmsSendResponse> forwardSms(SmsSendListRequest req) {
        return client.post()
            .uri("/v2/sms/send/list")
            .bodyValue(req)
            .exchangeToMono(resp -> {
                if (resp.statusCode().is2xxSuccessful()) {
                    return resp.bodyToMono(SmsSendResponse.class);
                }
                return resp.bodyToMono(String.class)
                    .defaultIfEmpty("")
                    .flatMap(body -> Mono.error(new IndigitallException(HttpStatusCode.valueOf(resp.rawStatusCode()), body)));
            });
    }

    public static class IndigitallException extends RuntimeException {
        private final HttpStatusCode status;
        private final String responseBody;
        public IndigitallException(HttpStatusCode status, String responseBody) {
            super("Indigitall error " + status + ": " + responseBody);
            this.status = status;
            this.responseBody = responseBody;
        }
        public HttpStatusCode getStatus() { return status; }
        public String getResponseBody() { return responseBody; }
    }
}
