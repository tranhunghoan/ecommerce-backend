package com.tranhunghoan.Springboot.ecommercebackend.model.dao;

import com.tranhunghoan.Springboot.ecommercebackend.model.LocalUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface LocalUserDAO extends ListCrudRepository<LocalUser,Long> {
    Optional<LocalUser> findById(Long id);
    Optional<LocalUser> findByUsernameIgnoreCase(String username);

    Optional<LocalUser> findByEmailIgnoreCase(String email);
}
