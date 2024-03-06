package com.tranhunghoan.Springboot.ecommercebackend.service;

import com.tranhunghoan.Springboot.ecommercebackend.api.model.OrderBody;
import com.tranhunghoan.Springboot.ecommercebackend.model.*;
import com.tranhunghoan.Springboot.ecommercebackend.model.dao.AddressDAO;
import com.tranhunghoan.Springboot.ecommercebackend.model.dao.LocalUserDAO;
import com.tranhunghoan.Springboot.ecommercebackend.model.dao.WebOrderDAO;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private WebOrderDAO webOrderDAO;
    private LocalUserDAO localUserDAO;
    private AddressDAO addressDAO;


    public OrderService(WebOrderDAO webOrderDAO, LocalUserDAO localUserDAO, AddressDAO addressDAO) {
        this.webOrderDAO = webOrderDAO;
        this.localUserDAO = localUserDAO;
        this.addressDAO = addressDAO;
    }
    public void createOrder(LocalUser user, Address address, List<WebOrderQuantities> quantities) {
        WebOrder order = new WebOrder();
        order.setUser(user);
        order.setAddress(address);
        order.setQuantitieses(quantities);
    }
    public List<WebOrder> getOrders(LocalUser user)
    {
        return webOrderDAO.findByUser(user);
    }
}
