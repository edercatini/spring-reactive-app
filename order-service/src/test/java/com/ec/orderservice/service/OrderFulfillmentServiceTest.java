package com.ec.orderservice.service;

import com.ec.orderservice.client.ProductClient;
import com.ec.orderservice.client.UserClient;
import com.ec.orderservice.model.RequestContext;
import com.ec.orderservice.model.dto.PurchaseOrderRequestDto;
import com.ec.orderservice.model.dto.product.ProductDto;
import com.ec.orderservice.model.dto.user.TransactionResponseDto;
import com.ec.orderservice.model.entity.PurchaseOrder;
import com.ec.orderservice.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.ec.orderservice.mock.ProductDtoMock.productDtoMock;
import static com.ec.orderservice.mock.PurchaseOrderMock.purchaseOrderMock;
import static com.ec.orderservice.mock.PurchaseOrderRequestDtoMock.purchaseOrderRequestDtoMock;
import static com.ec.orderservice.mock.RequestContextMock.requestContextMock;
import static com.ec.orderservice.mock.TransactionResponseDtoMock.transactionResponseDtoMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderFulfillmentServiceTest {

    @InjectMocks
    private OrderFulfillmentService service;

    @Mock
    private ProductClient productClient;

    @Mock
    private UserClient userClient;

    @Mock
    private PurchaseOrderRepository repository;

    private ProductDto productDto;
    private PurchaseOrder order;
    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private RequestContext context;
    private TransactionResponseDto transactionResponseDto;

    @BeforeEach
    public void setup() {
        productDto = productDtoMock();
        order = purchaseOrderMock();
        purchaseOrderRequestDto = purchaseOrderRequestDtoMock();
        context = requestContextMock();
        transactionResponseDto = transactionResponseDtoMock();
    }

    @Test
    @DisplayName("Should query product data once")
    void productClientTest() {
        when(productClient.getProductById(anyString())).thenReturn(Mono.just(productDto));

        StepVerifier.create(service.productRequestResponse(context))
                .expectNextCount(1L)
                .verifyComplete();

        assertEquals(context.getProductDto(), productDto);
        verify(productClient).getProductById(anyString());
    }

    @Test
    @DisplayName("Should query user data once")
    void userClientTest() {
        when(userClient.authorizeTransaction(any())).thenReturn(Mono.just(transactionResponseDto));

        StepVerifier.create(service.userRequestResponse(context))
                .expectNextCount(1L)
                .verifyComplete();

        assertEquals(context.getTransactionResponseDto(), transactionResponseDto);
        verify(userClient).authorizeTransaction(any());
    }

    @Test
    @DisplayName("Must process order")
    void processOrder() {
        when(productClient.getProductById(anyString())).thenReturn(Mono.just(productDto));
        when(userClient.authorizeTransaction(any())).thenReturn(Mono.just(transactionResponseDto));
        when(repository.save(any())).thenReturn(order);

        StepVerifier.create(service.processOrder(Mono.just(purchaseOrderRequestDto)))
                .expectNextCount(1L)
                .verifyComplete();

        verify(productClient).getProductById(anyString());
        verify(userClient).authorizeTransaction(any());
        verify(repository).save(any());
    }
}