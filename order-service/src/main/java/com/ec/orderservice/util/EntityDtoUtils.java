package com.ec.orderservice.util;

import com.ec.orderservice.model.OrderStatus;
import com.ec.orderservice.model.RequestContext;
import com.ec.orderservice.model.TransactionStatus;
import com.ec.orderservice.model.dto.PurchaseOrderResponseDto;
import com.ec.orderservice.model.dto.user.TransactionRequestDto;
import com.ec.orderservice.model.entity.PurchaseOrder;

public class EntityDtoUtils {

    public static void setTransactionRequestDto(RequestContext context) {
        TransactionRequestDto dto = TransactionRequestDto.builder()
                .userId(context.getPurchaseOrderRequestDto().getUserId())
                .amount(context.getProductDto().getPrice())
                .build();

        context.setTransactionRequestDto(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext context) {
        return PurchaseOrder.builder()
                .userId(context.getPurchaseOrderRequestDto().getUserId())
                .productId(context.getPurchaseOrderRequestDto().getProductId())
                .amount(context.getProductDto().getPrice())
                .orderStatus(handleStatus(context.getTransactionResponseDto().getStatus()))
                .build();
    }

    public static OrderStatus handleStatus(TransactionStatus transactionStatus) {
        return transactionStatus.equals(TransactionStatus.APPROVED) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
    }

    public static PurchaseOrderResponseDto getPurchaseOrderResponse(PurchaseOrder order) {
        return PurchaseOrderResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .amount(order.getAmount())
                .status(order.getOrderStatus())
                .build();
    }
}