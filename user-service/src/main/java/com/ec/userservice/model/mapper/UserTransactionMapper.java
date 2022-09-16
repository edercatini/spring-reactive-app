package com.ec.userservice.model.mapper;

import com.ec.userservice.model.dto.TransactionRequestDto;
import com.ec.userservice.model.dto.TransactionResponseDto;
import com.ec.userservice.model.entity.UserTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTransactionMapper {

    UserTransaction toEntity(TransactionRequestDto dto);
    TransactionResponseDto toResponse(UserTransaction userTransaction);
    TransactionResponseDto requestToResponse(TransactionRequestDto dto);
}