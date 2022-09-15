package com.ec.productservice.controller;

import com.ec.productservice.model.dto.ProductDto;
import com.ec.productservice.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.ec.productservice.mock.ProductDtoMock.productDtoMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(ProductController.class)
final class ProductControllerTest {

    @MockBean
    private ProductService service;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Must retrieve all products")
    void findAll() {
        List<ProductDto> expectedResponses = List.of(productDtoMock(), productDtoMock(), productDtoMock());
        when(service.findAll()).thenReturn(Flux.fromIterable(expectedResponses));

        webTestClient.get()
                .uri("/product/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductDto.class)
                .value(results -> assertTrue(results.containsAll(expectedResponses)));

        verify(service).findAll();
    }

    @Test
    @DisplayName("Must retrieve product by its ID")
    void findById() {
        ProductDto response = productDtoMock();
        when(service.findById(anyString())).thenReturn(Mono.just(response));

        webTestClient.get()
                .uri("/product/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class)
                .value(result -> assertEquals(response, result));

        verify(service).findById(anyString());
    }

    @Test
    @DisplayName("Must save product once")
    void save() {
        ProductDto response = productDtoMock();
        when(service.save(any())).thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/product")
                .bodyValue(productDtoMock())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductDto.class)
                .value(result -> assertEquals(response, result));

        verify(service).save(any());
    }

    @Test
    @DisplayName("Must update product once")
    void update() {
        ProductDto response = productDtoMock();
        when(service.update(anyString(), any())).thenReturn(Mono.just(response));

        webTestClient.put()
                .uri("/product/{id}", 1)
                .bodyValue(productDtoMock())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class)
                .value(result -> assertEquals(response, result));

        verify(service).update(anyString(), any());
    }

    @Test
    @DisplayName("Must delete product once")
    void delete() {
        when(service.delete(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/product/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();

        verify(service).delete(anyString());
    }
}