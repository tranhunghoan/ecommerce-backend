package com.tranhunghoan.Springboot.ecommercebackend.api.model;

public record CreateOrderQuantityRequest(
    Long productId,
    Integer quantity
) {
}
