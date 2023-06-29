package com.thang.orderservice.util;

import com.thang.orderservice.dto.OrderStatus;
import com.thang.orderservice.dto.RequestContext;
import com.thang.orderservice.dto.TransactionRequestDto;
import com.thang.orderservice.dto.TransactionStatus;
import com.thang.orderservice.entity.PurchaseOrder;

public class EntityDtoUtil {

    public static void setTransactionRequestDto(RequestContext requestContext) {
        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setUserId(requestContext.getTransactionRequestDto().getUserId());
        dto.setAmount(requestContext.getProductDto().getPrice());
        requestContext.setTransactionRequestDto(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(requestContext.getProductDto().getId());
        purchaseOrder.setAmount(requestContext.getProductDto().getPrice());

        TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        purchaseOrder.setStatus(orderStatus);

        return purchaseOrder;
    }
}
