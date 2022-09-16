package com.ec.productservice.service;

import com.ec.productservice.model.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ProductSinkService {

    private final Sinks.Many<ProductDto> sink;

    public Sinks.EmitResult emit(ProductDto dto) {
        return sink.tryEmitNext(dto);
    }
}