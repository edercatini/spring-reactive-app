package com.ec.userservice.service;

import com.ec.userservice.model.dto.TransactionRequestDto;
import com.ec.userservice.model.dto.TransactionResponseDto;
import com.ec.userservice.model.entity.UserTransaction;
import com.ec.userservice.model.mapper.UserTransactionMapper;
import com.ec.userservice.repository.UserRepository;
import com.ec.userservice.repository.UserTransactionRepository;
import com.ec.userservice.utils.UserTransactionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final class UserTransactionServiceTest {

    @InjectMocks
    private UserTransactionService service;

    @Mock
    private UserTransactionMapper mapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTransactionRepository userTransactionRepository;

    @Mock
    private UserTransactionUtils utils;

    @Test
    @DisplayName("Must allow transaction")
    void allowTransaction() {
        when(userRepository.updateUserBalance(any(), any())).thenReturn(Mono.just(Boolean.TRUE));
        when(mapper.toEntity(any())).thenReturn(mock(UserTransaction.class));
        when(userTransactionRepository.save(any(UserTransaction.class))).thenReturn(Mono.just(mock(UserTransaction.class)));
        when(mapper.toResponse(any(UserTransaction.class))).thenReturn(mock(TransactionResponseDto.class));
        when(utils.setStatus(any(), any())).thenReturn(mock(TransactionResponseDto.class));

        StepVerifier.create(service.apply(mock(TransactionRequestDto.class)))
                .expectNextCount(1L)
                .verifyComplete();

        verify(userRepository).updateUserBalance(any(), any());
        verify(mapper).toEntity(any());
        verify(userTransactionRepository).save(any(UserTransaction.class));
        verify(mapper).toResponse(any(UserTransaction.class));
    }

    @Test
    @DisplayName("Must deny transaction")
    void denyTransaction() {
        when(userRepository.updateUserBalance(any(), any())).thenReturn(Mono.just(Boolean.FALSE));
        when(mapper.requestToResponse(any(TransactionRequestDto.class))).thenReturn(mock(TransactionResponseDto.class));
        when(utils.setStatus(any(), any())).thenReturn(mock(TransactionResponseDto.class));

        StepVerifier.create(service.apply(mock(TransactionRequestDto.class)))
                .expectNextCount(1L)
                .verifyComplete();

        verify(userRepository).updateUserBalance(any(), any());
        verify(mapper).requestToResponse(any(TransactionRequestDto.class));
        verify(utils).setStatus(any(), any());
    }

    @Test
    @DisplayName("Must retrieve user transactions once")
    void retrieveUserTransactions() {
        when(userTransactionRepository.findByUserId(anyInt())).thenReturn(Flux.just(mock(UserTransaction.class)));
        StepVerifier.create(service.findByUserId(1)).expectNextCount(1L).verifyComplete();
        verify(userTransactionRepository).findByUserId(anyInt());
    }
}