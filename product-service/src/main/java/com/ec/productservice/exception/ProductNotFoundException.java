package com.ec.productservice.exception;

public class ProductNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Product not found";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}