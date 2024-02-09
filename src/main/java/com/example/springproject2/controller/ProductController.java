package com.example.springproject2.controller;

import com.example.springproject2.entity.Product;
import com.example.springproject2.repository.ProductRepository;
import com.example.springproject2.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

  Logger logger = LogManager.getLogger(ProductController.class);

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;

  // Save operation
  @PostMapping("/products")
  public ResponseEntity<Product> saveProduct(@RequestBody Product product)
  {
    try {

      System.out.println("Saving the product");
      logger.info("Saving the Product that we got from client");
      Product product_ = productService.saveProduct(product);
      System.out.println("Product saved");
      logger.info("Product saved");
      return new ResponseEntity<>(product_, HttpStatus.OK);

    }catch (Exception e){

      logger.error("Product not saved");
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  

  @GetMapping("/productsList")
  public ResponseEntity<List<Product>> getAllProductsByManufacturer(@RequestParam(required = false) String manufacturer){
    List<Product> products = new ArrayList<>();


    products.addAll(productRepository.findByManufacturerContaining(manufacturer));
    return  new ResponseEntity<>(products,HttpStatus.OK);

  }

  // Read operation
  @GetMapping("/products")
  public ResponseEntity<List<Product>> fetchProductsList()
  {

    List<Product> products =  productService.fetchProductList();
    return new ResponseEntity<>(products,HttpStatus.OK);
  }

  // Update operation
  @PutMapping("/products/{id}")
  public ResponseEntity<Product> updateProduct(@RequestBody Product product,@PathVariable("id") long productId)
  {
    return productService.updateProduct(product,productId);

  }

  // Delete opertaion by ID
  @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable("id") long productId)
    {
        productService.deleteProductById(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
