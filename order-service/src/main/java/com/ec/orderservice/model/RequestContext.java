package com.ec.orderservice.model;

import com.ec.orderservice.model.dto.PurchaseOrderRequestDto;
import com.ec.orderservice.model.dto.product.ProductDto;
import com.ec.orderservice.model.dto.user.TransactionRequestDto;
import com.ec.orderservice.model.dto.user.TransactionResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestContext {

    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;
}