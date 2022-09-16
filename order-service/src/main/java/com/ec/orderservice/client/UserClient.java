package com.ec.orderservice.client;

import com.ec.orderservice.model.dto.user.TransactionRequestDto;
import com.ec.orderservice.model.dto.user.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto dto) {
        return webClient.post()
                .uri("transaction")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }
}