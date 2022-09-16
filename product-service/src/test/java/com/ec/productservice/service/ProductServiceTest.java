package com.ec.productservice.service;

import com.ec.productservice.exception.ProductNotFoundException;
import com.ec.productservice.model.dto.ProductDto;
import com.ec.productservice.model.entity.Product;
import com.ec.productservice.model.mapper.ProductMapper;
import com.ec.productservice.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductMapper mapper;

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductSinkService sink;

    @Test
    @DisplayName("Must retrieve all products once")
    void findAll() {
        when(repository.findAll()).thenReturn(Flux.just(mock(Product.class)));
        when(mapper.toDto(any(Product.class))).thenReturn(mock(ProductDto.class));

        StepVerifier.create(service.findAll())
                .expectNextCount(1L)
                .verifyComplete();

        verify(repository).findAll();
        verify(mapper).toDto(any(Product.class));
    }

    @Test
    @DisplayName("Must find product by id")
    void findById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(mock(Product.class)));
        when(mapper.toDto(any(Product.class))).thenReturn(mock(ProductDto.class));

        StepVerifier.create(service.findById("id"))
                .expectNextCount(1L)
                .verifyComplete();

        verify(repository).findById(anyString());
        verify(mapper).toDto(any(Product.class));
    }

    @Test
    @DisplayName("Must not find product by id")
    void mustNotFindAnything() {
        when(repository.findById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(service.findById("id")).expectError(ProductNotFoundException.class);

        verify(repository).findById(anyString());
        verify(mapper, times(0)).toDto(any(Product.class));
    }

    @Test
    @DisplayName("Must save product once")
    void save() {
        when(repository.save(any(Product.class))).thenReturn(Mono.just(mock(Product.class)));
        when(mapper.toDto(any(Product.class))).thenReturn(mock(ProductDto.class));
        when(mapper.toEntity(any(ProductDto.class))).thenReturn(mock(Product.class));
        when(sink.emit(any())).thenReturn(null);

        StepVerifier.create(service.save(Mono.just(mock(ProductDto.class))))
                .expectNextCount(1L)
                .verifyComplete();

        verify(repository).save(any(Product.class));
        verify(mapper).toDto(any(Product.class));
        verify(mapper).toEntity(any(ProductDto.class));
        verify(sink).emit(any());
    }

    @Test
    @DisplayName("Must update once")
    void update() {
        when(repository.findById(anyString())).thenReturn(Mono.just(mock(Product.class)));
        when(repository.save(any(Product.class))).thenReturn(Mono.just(mock(Product.class)));
        when(mapper.toDto(any(Product.class))).thenReturn(mock(ProductDto.class));
        when(mapper.toEntity(any(ProductDto.class))).thenReturn(mock(Product.class));

        StepVerifier.create(service.update("id", Mono.just(mock(ProductDto.class))))
                .expectNextCount(1L)
                .verifyComplete();

        verify(repository).findById(anyString());
        verify(repository).save(any(Product.class));
        verify(mapper).toDto(any(Product.class));
        verify(mapper).toEntity(any(ProductDto.class));
    }

    @Test
    @DisplayName("Must handle update exception if no product is found")
    void updateException() {
        when(repository.findById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(service.update("id", Mono.just(mock(ProductDto.class))))
                .expectError(ProductNotFoundException.class);

        verify(repository).findById(anyString());
        verify(repository, times(0)).save(any(Product.class));
        verify(mapper, times(0)).toDto(any(Product.class));
        verify(mapper, times(0)).toEntity(any(ProductDto.class));
    }

    @Test
    @DisplayName("Must delete once")
    void delete() {
        when(repository.findById(anyString())).thenReturn(Mono.just(mock(Product.class)));
        when(repository.delete(any(Product.class))).thenReturn(Mono.empty());

        StepVerifier.create(service.delete("id"))
                .verifyComplete();

        verify(repository).findById(anyString());
        verify(repository).delete(any(Product.class));
    }

    @Test
    @DisplayName("Must handle delete exception if no product is found")
    void deleteException() {
        when(repository.findById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(service.delete("id"))
                .expectError(ProductNotFoundException.class);

        verify(repository).findById(anyString());
        verify(repository, times(0)).delete(any(Product.class));
    }
}