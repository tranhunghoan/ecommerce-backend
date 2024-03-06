package com.tranhunghoan.Springboot.ecommercebackend.model.dao;

import com.tranhunghoan.Springboot.ecommercebackend.model.Inventory;
import org.springframework.data.repository.ListCrudRepository;

public interface InventoryDAO extends ListCrudRepository<Inventory,Long> {
}
