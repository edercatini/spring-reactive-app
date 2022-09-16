package com.ec.orderservice.mock;

import com.ec.orderservice.model.TransactionStatus;
import com.ec.orderservice.model.dto.user.TransactionResponseDto;

public class TransactionResponseDtoMock {

    public static TransactionResponseDto transactionResponseDtoMock() {
        return TransactionResponseDto.builder()
                .userId(1)
                .amount(100)
                .status(TransactionStatus.APPROVED)
                .build();
    }
}