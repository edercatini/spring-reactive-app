package com.ec.orderservice.service;

import com.ec.orderservice.model.dto.PurchaseOrderResponseDto;
import com.ec.orderservice.repository.PurchaseOrderRepository;
import com.ec.orderservice.util.EntityDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final PurchaseOrderRepository repository;

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(Integer userId) {
        return Flux.fromStream(() -> repository.findByUserId(userId).stream())
                .map(EntityDtoUtils::getPurchaseOrderResponse)
                .subscribeOn(Schedulers.boundedElastic());
    }
}