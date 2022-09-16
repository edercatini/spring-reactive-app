package com.ec.orderservice.controller;

import com.ec.orderservice.model.dto.PurchaseOrderRequestDto;
import com.ec.orderservice.model.dto.PurchaseOrderResponseDto;
import com.ec.orderservice.service.OrderFulfillmentService;
import com.ec.orderservice.service.OrderQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@WebFluxTest(PurchaseOrderController.class)
class PurchaseOrderControllerTest {

    @MockBean
    private OrderFulfillmentService service;

    @MockBean
    private OrderQueryService queryService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Must create order")
    void createOrder() {
        when(service.processOrder(any())).thenReturn(Mono.just(PurchaseOrderResponseDto.builder().build()));

        webTestClient.post()
                .uri("/order")
                .bodyValue(PurchaseOrderRequestDto.builder().build())
                .exchange()
                .expectStatus().isCreated();

        verify(service).processOrder(any());
    }

    @Test
    @DisplayName("Must query user orders")
    void queryOrder() {
        when(queryService.getProductsByUserId(anyInt())).thenReturn(Flux.empty());

        webTestClient.get()
                .uri("/order/user/{userId}", 1)
                .exchange()
                .expectStatus().isOk();

        verify(queryService).getProductsByUserId(anyInt());
    }
}