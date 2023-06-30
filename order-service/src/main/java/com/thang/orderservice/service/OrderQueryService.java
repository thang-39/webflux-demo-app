package com.thang.orderservice.service;

import com.thang.orderservice.dto.PurchaseOrderResponseDto;
import com.thang.orderservice.repository.PurchaseOrderRepository;
import com.thang.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final PurchaseOrderRepository orderRepository;

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId) {
        // do not do this - this is blocking
//        List<PurchaseOrder> purchaseOrders = orderRepository.findByUserId(userId);
//        Flux.fromIterable(purchaseOrders)
        // do like this - convert it to stream and put it in Supplier like this
        // it is lazily way - we dont call it outside like above
        return Flux.fromStream(() -> orderRepository.findByUserId(userId).stream())
                .map(purchaseOrder -> EntityDtoUtil.getPurchaseOrderResponseDto(purchaseOrder))
                .subscribeOn(Schedulers.boundedElastic());

    }
}
