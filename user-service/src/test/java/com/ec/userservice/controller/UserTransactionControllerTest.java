package com.ec.userservice.controller;

import com.ec.userservice.model.dto.TransactionRequestDto;
import com.ec.userservice.model.dto.TransactionResponseDto;
import com.ec.userservice.model.entity.UserTransaction;
import com.ec.userservice.service.UserTransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.ec.userservice.mock.TransactionResponseDtoMock.transactionResponseDtoMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(UserTransactionController.class)
class UserTransactionControllerTest {

    @MockBean
    private UserTransactionService service;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Must create transaction once")
    void create() {
        TransactionResponseDto response = transactionResponseDtoMock();
        when(service.apply(any())).thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/user/transaction")
                .bodyValue(TransactionRequestDto.builder().build())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionResponseDto.class)
                .value(result -> assertEquals(response, result));

        verify(service).apply(any());
    }

    @Test
    @DisplayName("Must retrieve user transactions")
    void retrieveUserTransactions() {
        when(service.findByUserId(anyInt())).thenReturn(Flux.just(UserTransaction.builder().build()));

        webTestClient.get()
                .uri("/user/transaction/{userId}", 1)
                .exchange()
                .expectStatus().isOk();

        verify(service).findByUserId(anyInt());
    }
}