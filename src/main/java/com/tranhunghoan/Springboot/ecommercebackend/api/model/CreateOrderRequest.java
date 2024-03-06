package com.tranhunghoan.Springboot.ecommercebackend.api.model;

import java.util.List;

public record CreateOrderRequest(
    List<CreateOrderQuantityRequest> quantities
) {
}
