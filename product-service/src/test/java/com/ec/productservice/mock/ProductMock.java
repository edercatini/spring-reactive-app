package com.ec.productservice.mock;

import com.ec.productservice.model.entity.Product;

public final class ProductMock {

    public static Product productMock() {
        return Product.builder()
                .id("id")
                .description("product")
                .price(500)
                .build();
    }
}