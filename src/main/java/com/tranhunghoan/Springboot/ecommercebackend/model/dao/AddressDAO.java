package com.tranhunghoan.Springboot.ecommercebackend.model.dao;

import com.tranhunghoan.Springboot.ecommercebackend.model.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AddressDAO extends ListCrudRepository<Address, Long> {

    List<Address> findByUser_Id(Long user_id);

}
