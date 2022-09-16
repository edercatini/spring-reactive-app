package com.ec.userservice.utils;

import com.ec.userservice.model.TransactionStatus;
import com.ec.userservice.model.dto.TransactionResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserTransactionUtilsTest {

    @Spy
    private UserTransactionUtils utils;

    @Test
    void mustSetStatus() {
        TransactionResponseDto dto = TransactionResponseDto.builder().build();
        utils.setStatus(dto, TransactionStatus.APPROVED);
        assertEquals(TransactionStatus.APPROVED, dto.getStatus());
    }
}