package com.tranhunghoan.Springboot.ecommercebackend.service;

import com.tranhunghoan.Springboot.ecommercebackend.api.exception.NotFoundException;
import com.tranhunghoan.Springboot.ecommercebackend.api.model.ProductBody;
import com.tranhunghoan.Springboot.ecommercebackend.model.Inventory;
import com.tranhunghoan.Springboot.ecommercebackend.model.Product;
import com.tranhunghoan.Springboot.ecommercebackend.model.dao.InventoryDAO;
import com.tranhunghoan.Springboot.ecommercebackend.model.dao.ProductDAO;
import org.hibernate.annotations.NotFound;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductDAO productDAO;




    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    @Transactional
    public void addProduct(ProductBody productBody) {
        Inventory inventory = new Inventory();
        inventory.setQuantity(productBody.getQuantity());

        Product product = new Product();
        product.setName(productBody.getName());
        product.setShortDescription(productBody.getShortDescription());
        product.setLongDescription(productBody.getLongDescription());
        product.setPrice(productBody.getPrice());

        product.setInventory(inventory);
        productDAO.save(product);
    }
    @Transactional
    public void deleteProduct(Long productId) throws NotFoundException {
        Optional<Product> opProduct = productDAO.findById(productId);
        if(opProduct.isPresent())
        {
            Product product = opProduct.get();
            productDAO.delete(product);
        }else {
            throw new NotFoundException("Product not found with id: " + productId);
        }
    }
    @Transactional
    public Product updateProduct(Long productId,ProductBody productBody) throws NotFoundException {
        Optional<Product> optionalProduct = productDAO.findById(productId);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setName(productBody.getName());
            existingProduct.setShortDescription(productBody.getShortDescription());
            existingProduct.setLongDescription(productBody.getLongDescription());
            existingProduct.setPrice(productBody.getPrice());

             return productDAO.save(existingProduct);
        } else {
            throw new NotFoundException("Product not found with id: " + productId);
        }
    }
    public List<Product> getProducts(){
        return productDAO.findAll();
    }
}
