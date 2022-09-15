package com.ec.productservice.model.mapper;

import com.ec.productservice.model.dto.ProductDto;
import com.ec.productservice.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDto dto);
    ProductDto toDto(Product product);
}