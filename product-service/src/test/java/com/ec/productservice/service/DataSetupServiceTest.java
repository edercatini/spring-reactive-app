package com.ec.productservice.service;

import com.ec.productservice.model.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataSetupServiceTest {

    @InjectMocks
    private DataSetupService dataSetupService;

    @Mock
    private ProductService productService;

    @Test
    @DisplayName("Must insert default data")
    void insert() {
        when(productService.save(any())).thenReturn(Mono.just(mock(ProductDto.class)));
        dataSetupService.run();
        verify(productService, times(4)).save(any());
    }
}