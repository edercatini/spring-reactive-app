package com.ec.userservice.service;

import com.ec.userservice.model.TransactionStatus;
import com.ec.userservice.model.dto.TransactionRequestDto;
import com.ec.userservice.model.dto.TransactionResponseDto;
import com.ec.userservice.model.entity.UserTransaction;
import com.ec.userservice.model.mapper.UserTransactionMapper;
import com.ec.userservice.repository.UserRepository;
import com.ec.userservice.repository.UserTransactionRepository;
import com.ec.userservice.utils.UserTransactionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class UserTransactionService {

    private final UserTransactionMapper mapper;
    private final UserRepository userRepository;
    private final UserTransactionRepository userTransactionRepository;
    private final UserTransactionUtils utils;
    private final Predicate<Boolean> wasUpdated = Boolean::booleanValue;

    public Mono<TransactionResponseDto> apply(TransactionRequestDto dto) {
        return userRepository.updateUserBalance(dto.getUserId(), dto.getAmount())
                .filter(wasUpdated)
                .map(updated -> mapper.toEntity(dto))
                .flatMap(userTransactionRepository::save)
                .map(mapper::toResponse)
                .map(transactionResponseDto -> utils.setStatus(transactionResponseDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(utils.setStatus(mapper.requestToResponse(dto), TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> findByUserId(Integer userId) {
        return userTransactionRepository.findByUserId(userId);
    }
}