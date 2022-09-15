package com.ec.productservice.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Product {

    @Id
    private String id;
    private String description;
    private Integer price;
}