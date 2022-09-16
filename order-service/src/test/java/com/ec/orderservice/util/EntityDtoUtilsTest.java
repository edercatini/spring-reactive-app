package com.ec.orderservice.util;

import com.ec.orderservice.model.OrderStatus;
import com.ec.orderservice.model.RequestContext;
import com.ec.orderservice.model.TransactionStatus;
import com.ec.orderservice.model.dto.PurchaseOrderRequestDto;
import com.ec.orderservice.model.dto.PurchaseOrderResponseDto;
import com.ec.orderservice.model.dto.product.ProductDto;
import com.ec.orderservice.model.entity.PurchaseOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ec.orderservice.mock.ProductDtoMock.productDtoMock;
import static com.ec.orderservice.mock.PurchaseOrderMock.purchaseOrderMock;
import static com.ec.orderservice.mock.PurchaseOrderRequestDtoMock.purchaseOrderRequestDtoMock;
import static com.ec.orderservice.mock.RequestContextMock.requestContextMock;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDtoUtilsTest {

    private RequestContext context;
    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private PurchaseOrder purchaseOrder;

    @BeforeEach
    public void setup() {
        context = requestContextMock();
        purchaseOrderRequestDto = purchaseOrderRequestDtoMock();
        productDto = productDtoMock();
        purchaseOrder = purchaseOrderMock();
    }

    @Test
    @DisplayName("Set transaction request dto on context")
    void transactionRequestOnContext() {
        EntityDtoUtils.setTransactionRequestDto(context);
        assertEquals(purchaseOrderRequestDto.getUserId(), context.getTransactionRequestDto().getUserId());
        assertEquals(productDto.getPrice(), context.getTransactionRequestDto().getAmount());
    }

    @Test
    @DisplayName("Get purchase order")
    void getOrder() {
        PurchaseOrder result = EntityDtoUtils.getPurchaseOrder(context);
        assertEquals(context.getPurchaseOrderRequestDto().getUserId(), result.getUserId());
        assertEquals(context.getPurchaseOrderRequestDto().getProductId(), result.getProductId());
        assertEquals(context.getProductDto().getPrice(), result.getAmount());
        assertEquals(context.getTransactionResponseDto().getStatus(), TransactionStatus.APPROVED);
    }

    @Test
    @DisplayName("Get purchase order response")
    void getOrderResponse() {
        PurchaseOrderResponseDto result = EntityDtoUtils.getPurchaseOrderResponse(purchaseOrder);
        assertEquals(result.getOrderId(), purchaseOrder.getId());
        assertEquals(result.getUserId(), purchaseOrder.getUserId());
        assertEquals(result.getProductId(), purchaseOrder.getProductId());
        assertEquals(result.getAmount(), purchaseOrder.getAmount());
        assertEquals(result.getStatus(), purchaseOrder.getOrderStatus());
    }

    @Test
    @DisplayName("Must handle status when transaction approved")
    void transactionApproved() {
        assertEquals(OrderStatus.COMPLETED, EntityDtoUtils.handleStatus(TransactionStatus.APPROVED));
    }

    @Test
    @DisplayName("Must handle status when transaction declined")
    void transactionDeclined() {
        assertEquals(OrderStatus.FAILED, EntityDtoUtils.handleStatus(TransactionStatus.DECLINED));
    }
}