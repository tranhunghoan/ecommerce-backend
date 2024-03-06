package com.tranhunghoan.Springboot.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tranhunghoan.Springboot.ecommercebackend.api.model.CreateOrderQuantityRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "web_order_quantities")
@Getter
@Setter
public class WebOrderQuantities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private WebOrder order;

    public WebOrderQuantities(CreateOrderQuantityRequest request) {
        this.product = new Product();
        this.product.setId(request.productId());
        this.quantity = request.quantity();
    }
}
