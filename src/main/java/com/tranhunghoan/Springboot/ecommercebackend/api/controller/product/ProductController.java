package com.tranhunghoan.Springboot.ecommercebackend.api.controller.product;

import com.tranhunghoan.Springboot.ecommercebackend.api.exception.NotFoundException;
import com.tranhunghoan.Springboot.ecommercebackend.api.model.ProductBody;
import com.tranhunghoan.Springboot.ecommercebackend.model.Product;
import com.tranhunghoan.Springboot.ecommercebackend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController

@RequestMapping("/product")
public class ProductController {

    private ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProduct(@RequestBody ProductBody productBody) {
        productService.addProduct(productBody);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) throws NotFoundException {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody ProductBody updatedProductBody) throws NotFoundException {
        Product updatedProduct = productService.updateProduct(productId, updatedProductBody);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
