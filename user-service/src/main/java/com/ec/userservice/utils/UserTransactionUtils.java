package com.ec.userservice.utils;

import com.ec.userservice.model.TransactionStatus;
import com.ec.userservice.model.dto.TransactionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserTransactionUtils {

    public TransactionResponseDto setStatus(TransactionResponseDto dto, TransactionStatus status) {
        return dto.setStatus(status);
    }
}