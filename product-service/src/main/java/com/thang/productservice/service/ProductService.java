package com.thang.productservice.service;

import com.thang.productservice.dto.ProductDto;
import com.thang.productservice.repository.ProductRepository;
import com.thang.productservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Flux<ProductDto> getAll() {
        return this.repository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return this.repository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.repository::insert)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> updateProduct(String id,
                                          Mono<ProductDto> productDtoMono) {
        return this.repository.findById(id)
                .flatMap(p -> productDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.repository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return this.repository.deleteById(id);
    }

    public Flux<ProductDto> getProductByPriceRangeThang(int min, int max) {
        return repository.findByPriceBetween(min, max)
                .concatWith(repository.findByPrice(min))
                .concatWith(repository.findByPrice(max))
                .map(EntityDtoUtil::toDto);
    }

    public Flux<ProductDto> getProductByPriceRange(int min, int max) {
        return repository.findByPriceBetween(Range.closed(min,max))
                .map(EntityDtoUtil::toDto);
    }
}
