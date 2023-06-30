package com.thang.orderservice.service;

import com.thang.orderservice.client.ProductClient;
import com.thang.orderservice.client.UserClient;
import com.thang.orderservice.dto.PurchaseOrderRequestDto;
import com.thang.orderservice.dto.PurchaseOrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class OrderCombineService {
    @Autowired
    private ProductClient productClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private OrderFulfillmentService orderService;
    public Flux<PurchaseOrderResponseDto> combine() {
        return Flux.zip(userClient.getUsers(), productClient.getProducts())
                .map(tuple2 -> new PurchaseOrderRequestDto(tuple2.getT1().getId(), tuple2.getT2().getId()))
                .flatMap(purchaseOrderRequestDto -> {
                    Mono<PurchaseOrderRequestDto> purchaseOrderRequestDtoMono = Mono.just(purchaseOrderRequestDto);
                    return orderService.processOrder(purchaseOrderRequestDtoMono);
                });
    }
}
