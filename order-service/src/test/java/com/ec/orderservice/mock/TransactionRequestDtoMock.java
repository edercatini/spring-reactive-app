package com.ec.orderservice.mock;

import com.ec.orderservice.model.dto.user.TransactionRequestDto;

public class TransactionRequestDtoMock {

    public static TransactionRequestDto transactionRequestDtoMock() {
        return TransactionRequestDto.builder()
                .userId(1)
                .amount(100)
                .build();
    }
}