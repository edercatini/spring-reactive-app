package com.ec.userservice.controller;

import com.ec.userservice.model.dto.TransactionRequestDto;
import com.ec.userservice.model.dto.TransactionResponseDto;
import com.ec.userservice.model.entity.UserTransaction;
import com.ec.userservice.service.UserTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class UserTransactionController {

    private final UserTransactionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionResponseDto> create(@RequestBody Mono<TransactionRequestDto> dto) {
        return dto.flatMap(service::apply);
    }

    @GetMapping("{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserTransaction> findByUserId(@PathVariable Integer userId) {
        return service.findByUserId(userId);
    }
}