package com.ec.orderservice.mock;

import com.ec.orderservice.model.dto.product.ProductDto;

public class ProductDtoMock {

    public static ProductDto productDtoMock() {
        return ProductDto.builder()
                .id("1")
                .description("description")
                .price(100)
                .build();
    }
}