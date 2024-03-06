package com.tranhunghoan.Springboot.ecommercebackend.api.controller.order;

import com.tranhunghoan.Springboot.ecommercebackend.api.model.CreateOrderRequest;
import com.tranhunghoan.Springboot.ecommercebackend.model.LocalUser;
import com.tranhunghoan.Springboot.ecommercebackend.model.WebOrder;
import com.tranhunghoan.Springboot.ecommercebackend.model.dao.AddressDAO;
import com.tranhunghoan.Springboot.ecommercebackend.model.dao.LocalUserDAO;
import com.tranhunghoan.Springboot.ecommercebackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final LocalUserDAO localUserDAO;

    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user) {
        return orderService.getOrders(user);
    }

    @PostMapping
    public void createOrders(@AuthenticationPrincipal LocalUser user, @RequestBody CreateOrderRequest request) {
        var user1 = localUserDAO.findById(user.getId()).orElseThrow();
        orderService.createOrder(user, user1.getAddresses().get(0), request.quantities());
    }
}
