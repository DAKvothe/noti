package com.santander.cpe.porweb.integration.indigitall.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(IndigitallProperties.class)
public class IndigitallWebClientConfig {

    @Bean
    public WebClient indigitallWebClient(IndigitallProperties props) {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, props.getTimeoutMs())
            .responseTimeout(Duration.ofMillis(props.getTimeoutMs()))
            .doOnConnected(conn -> conn
                .addHandlerLast(new ReadTimeoutHandler(props.getTimeoutMs(), TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(props.getTimeoutMs(), TimeUnit.MILLISECONDS)));

        return WebClient.builder()
            .baseUrl(props.getBaseUrl())
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "ServerKey " + props.getServerKey())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .filter(logRequest())
            .filter(logResponse())
            .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(req -> reactor.core.publisher.Mono.just(req));
    }
    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(res -> reactor.core.publisher.Mono.just(res));
    }
}
