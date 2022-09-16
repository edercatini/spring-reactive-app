package com.ec.orderservice.mock;

import com.ec.orderservice.model.dto.PurchaseOrderRequestDto;

public class PurchaseOrderRequestDtoMock {

    public static PurchaseOrderRequestDto purchaseOrderRequestDtoMock() {
        return PurchaseOrderRequestDto.builder()
                .userId(1)
                .productId("productId")
                .build();
    }
}