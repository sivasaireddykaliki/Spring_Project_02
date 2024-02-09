package com.example.springproject2.service;

import com.example.springproject2.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    // create or save operation
    Product saveProduct(Product product);

    // Read operation
    ResponseEntity<List<Product>> fetchProductList();


    //Update operation by Id
    ResponseEntity<Product> updateProduct(Product product, Long productId);

    //Delete operation by Id
    void deleteProductById(Long productId);

    // read operation by manufacturer
    ResponseEntity<List<Product>> fetchProductListByManufacturer(String manufacturer);

    // read operation by name
    ResponseEntity<List<Product>> fetchProductListByName(String name);
}
