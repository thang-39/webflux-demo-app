package com.thang.orderservice.service;

import com.thang.orderservice.client.ProductClient;
import com.thang.orderservice.client.UserClient;
import com.thang.orderservice.dto.PurchaseOrderRequestDto;
import com.thang.orderservice.dto.PurchaseOrderResponseDto;
import com.thang.orderservice.dto.RequestContext;
import com.thang.orderservice.repository.PurchaseOrderRepository;
import com.thang.orderservice.util.EntityDtoUtil;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final PurchaseOrderRepository orderRepository;

    private final ProductClient productClient;

    private final UserClient userClient;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return requestDtoMono.map(dto -> new RequestContext(dto))
                .flatMap(rc -> productRequestResponse(rc))
                .doOnNext(rc -> EntityDtoUtil.setTransactionRequestDto(rc))
                .flatMap(rc -> userRequestResponse(rc))
                .map(rc -> EntityDtoUtil.getPurchaseOrder(rc))
                .publishOn(Schedulers.boundedElastic())
                .map(purchaseOrder -> orderRepository.save(purchaseOrder)) // blocking
                .map(purchaseOrder -> EntityDtoUtil.getPurchaseOrderResponseDto(purchaseOrder))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc) {
        return productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(productDto -> rc.setProductDto(productDto))
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext rc) {
        return userClient.authorizeTransaction(rc.getTransactionRequestDto())
                .doOnNext(transactionResponseDto -> rc.setTransactionResponseDto(transactionResponseDto))
                .thenReturn(rc);
    }
}
