package com.ec.productservice.mock;

import com.ec.productservice.model.dto.ProductDto;

public final class ProductDtoMock {

    public static ProductDto productDtoMock() {
        return ProductDto.builder()
                .id("id")
                .description("product")
                .price(500)
                .build();
    }
}