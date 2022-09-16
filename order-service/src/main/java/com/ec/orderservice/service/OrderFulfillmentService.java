package com.ec.orderservice.service;

import com.ec.orderservice.client.ProductClient;
import com.ec.orderservice.client.UserClient;
import com.ec.orderservice.model.RequestContext;
import com.ec.orderservice.model.dto.PurchaseOrderRequestDto;
import com.ec.orderservice.model.dto.PurchaseOrderResponseDto;
import com.ec.orderservice.repository.PurchaseOrderRepository;
import com.ec.orderservice.util.EntityDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final ProductClient productClient;
    private final UserClient userClient;
    private final PurchaseOrderRepository repository;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> dto) {
        return dto
                .map(purchaseOrderRequestDto -> RequestContext.builder().purchaseOrderRequestDto(purchaseOrderRequestDto).build())
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtils::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtils::getPurchaseOrder)
                .map(repository::save)
                .map(EntityDtoUtils::getPurchaseOrderResponse)
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<RequestContext> productRequestResponse(RequestContext context) {
        return productClient.getProductById(context.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(context::setProductDto)
                .thenReturn(context);
    }

    public Mono<RequestContext> userRequestResponse(RequestContext context) {
        return userClient.authorizeTransaction(context.getTransactionRequestDto())
                .doOnNext(context::setTransactionResponseDto)
                .thenReturn(context);
    }
}