package com.ec.orderservice.mock;

import com.ec.orderservice.model.RequestContext;

import static com.ec.orderservice.mock.ProductDtoMock.productDtoMock;
import static com.ec.orderservice.mock.PurchaseOrderRequestDtoMock.purchaseOrderRequestDtoMock;
import static com.ec.orderservice.mock.TransactionRequestDtoMock.transactionRequestDtoMock;
import static com.ec.orderservice.mock.TransactionResponseDtoMock.transactionResponseDtoMock;

public class RequestContextMock {

    public static RequestContext requestContextMock() {
        return RequestContext.builder()
                .purchaseOrderRequestDto(purchaseOrderRequestDtoMock())
                .productDto(productDtoMock())
                .transactionRequestDto(transactionRequestDtoMock())
                .transactionResponseDto(transactionResponseDtoMock())
                .build();
    }
}