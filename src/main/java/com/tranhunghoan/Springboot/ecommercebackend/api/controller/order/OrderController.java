package com.tranhunghoan.Springboot.ecommercebackend.api.controller.order;

import com.tranhunghoan.Springboot.ecommercebackend.model.LocalUser;
import com.tranhunghoan.Springboot.ecommercebackend.model.WebOrder;
import com.tranhunghoan.Springboot.ecommercebackend.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user){
        return orderService.getOrders(user);
    }

    @PostMapping
    public void createOrders() {

    }

}
