package com.ec.productservice.service;

import com.ec.productservice.model.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

    private final ProductService service;

    @Override
    @SneakyThrows
    public void run(String... args) {
        List<ProductDto> products = List.of(
                ProductDto.builder().description("4k-tv").price(1000).build(),
                ProductDto.builder().description("slr-camera").price(750).build(),
                ProductDto.builder().description("iphone").price(800).build(),
                ProductDto.builder().description("headphone").price(100).build()
        );

        Flux.fromIterable(products)
                .flatMap(productDto -> service.save(Mono.just(productDto)))
                .subscribe(System.out::println);
    }
}