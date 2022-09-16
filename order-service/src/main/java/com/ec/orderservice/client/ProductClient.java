package com.ec.orderservice.client;

import com.ec.orderservice.model.dto.product.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${product.service.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<ProductDto> getProductById(String productId) {
        return webClient.get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }
}