package com.thang.orderservice.controller;

import com.thang.orderservice.dto.PurchaseOrderRequestDto;
import com.thang.orderservice.dto.PurchaseOrderResponseDto;
import com.thang.orderservice.service.OrderFulfillmentService;
import com.thang.orderservice.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService queryService;

    @PostMapping
    public Mono<PurchaseOrderResponseDto> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return orderFulfillmentService.processOrder(requestDtoMono);
    }

    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable int userId) {
        return queryService.getProductsByUserId(userId);
    }

}
