package com.thang.productservice.repository;

import com.thang.productservice.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findByPrice(int price);

    // > min & < max ( not have = min & = max)
    Flux<Product> findByPriceBetween(int min, int max);

    // use Range<Integer> >= min & <= max
    Flux<Product> findByPriceBetween(Range<Integer> range);

    // not work
//    Flux<Product> findByPriceIn(Range<Integer> range);
//    Flux<Product> findByPriceGreaterThanEqualAndPriceLessThanEqual(int min, int max);
}
