package com.ec.orderservice.controller;

import com.ec.orderservice.model.dto.PurchaseOrderRequestDto;
import com.ec.orderservice.model.dto.PurchaseOrderResponseDto;
import com.ec.orderservice.service.OrderFulfillmentService;
import com.ec.orderservice.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final OrderFulfillmentService service;
    private final OrderQueryService queryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PurchaseOrderResponseDto> order(@RequestBody Mono<PurchaseOrderRequestDto> dto) {
        return service.processOrder(dto);
    }

    @GetMapping("user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable Integer userId) {
        return queryService.getProductsByUserId(userId);
    }
}