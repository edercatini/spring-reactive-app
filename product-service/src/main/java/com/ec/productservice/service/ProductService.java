package com.ec.productservice.service;

import com.ec.productservice.exception.ProductNotFoundException;
import com.ec.productservice.model.dto.ProductDto;
import com.ec.productservice.model.mapper.ProductMapper;
import com.ec.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final ProductSinkService sink;

    public Flux<ProductDto> findAll() {
        return repository
                .findAll()
                .map(mapper::toDto);
    }

    public Mono<ProductDto> findById(String id) {
        return repository
                .findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()))
                .map(mapper::toDto);
    }

    public Mono<ProductDto> save(Mono<ProductDto> dto) {
        return dto.map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDto)
                .doOnNext(sink::emit);
    }

    public Mono<ProductDto> update(String id, Mono<ProductDto> dto) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()))
                .flatMap(product -> dto.map(mapper::toEntity).doOnNext(entity -> entity.setId(id)))
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()))
                .flatMap(repository::delete);
    }
}