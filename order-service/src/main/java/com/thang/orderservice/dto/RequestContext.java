package com.thang.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class RequestContext {

    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }
}
