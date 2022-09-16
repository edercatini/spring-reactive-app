package com.ec.orderservice.mock;

import com.ec.orderservice.model.OrderStatus;
import com.ec.orderservice.model.entity.PurchaseOrder;

public class PurchaseOrderMock {

    public static PurchaseOrder purchaseOrderMock() {
        return PurchaseOrder.builder()
                .id(1)
                .userId(1)
                .productId("1")
                .amount(100)
                .orderStatus(OrderStatus.COMPLETED)
                .build();
    }
}