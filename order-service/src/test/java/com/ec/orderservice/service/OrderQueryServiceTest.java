package com.ec.orderservice.service;

import com.ec.orderservice.model.entity.PurchaseOrder;
import com.ec.orderservice.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderQueryServiceTest {

    @InjectMocks
    private OrderQueryService service;

    @Mock
    private PurchaseOrderRepository repository;

    @Test
    @DisplayName("Must retrieve user orders once")
    void queryAll() {
        when(repository.findByUserId(anyInt())).thenReturn(List.of(mock(PurchaseOrder.class)));

        StepVerifier.create(service.getProductsByUserId(1))
                .expectNextCount(1L)
                .verifyComplete();

        verify(repository).findByUserId(anyInt());
    }
}