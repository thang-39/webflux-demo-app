package com.thang.orderservice;

import com.thang.orderservice.client.ProductClient;
import com.thang.orderservice.client.UserClient;
import com.thang.orderservice.dto.ProductDto;
import com.thang.orderservice.dto.PurchaseOrderRequestDto;
import com.thang.orderservice.dto.PurchaseOrderResponseDto;
import com.thang.orderservice.dto.UserDto;
import com.thang.orderservice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private ProductClient productClient;

	@Autowired
	private UserClient userClient;

	@Autowired
	private OrderFulfillmentService fulfillmentService;



	@Test
	void contextLoads() {

		Flux<PurchaseOrderResponseDto> dtoFlux = Flux.zip(userClient.getUsers(), productClient.getProducts())
				.map(tuple2 -> new PurchaseOrderRequestDto(tuple2.getT1().getId(), tuple2.getT2().getId()))
				.flatMap(purchaseOrderRequestDto -> fulfillmentService.processOrder(Mono.just(purchaseOrderRequestDto)))
				.doOnNext(System.out::println);

		StepVerifier.create(dtoFlux)
				.expectNextCount(4)
				.verifyComplete();
	}



}
