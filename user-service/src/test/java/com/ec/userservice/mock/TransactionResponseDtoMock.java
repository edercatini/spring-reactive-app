package com.ec.userservice.mock;

import com.ec.userservice.model.dto.TransactionResponseDto;

public final class TransactionResponseDtoMock {

    public static TransactionResponseDto transactionResponseDtoMock() {
        return TransactionResponseDto.builder()
                .userId(1)
                .amount(500)
                .build();
    }
}