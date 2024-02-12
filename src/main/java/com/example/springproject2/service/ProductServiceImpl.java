package com.example.springproject2.service;

import com.example.springproject2.entity.Product;
import com.example.springproject2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    // we delegate function calls to ProductRepository for crud operations.

    // save the product
    @Override
    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

    // get the list of products
    @Override
    public ResponseEntity<Product[]> fetchProductList() {

        List<Product> products_ =  productRepository.findAll();

        if(products_ == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        else{
            Product[] products=products_.toArray(new Product[0]);
            return new ResponseEntity<>(products,HttpStatus.OK);
        }
    }

    // update the products
    @Override
    public ResponseEntity<Product> updateProduct(Product product, Long productId) {

        // get Product by Id from database

        Optional<Product> _product = productRepository.findById(productId);

        if(_product.isPresent()) {
            // update fields
            Product updatedProduct = _product.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setAvailability(product.isAvailability());
            updatedProduct.setManufacturer(product.getManufacturer());

            //save product
            return new ResponseEntity<>(productRepository.save(updatedProduct),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // delete the product by id
    @Override
    public void deleteProductById(Long productId) {

        productRepository.deleteById(productId);
    }

    // find list of products by manufacturer
    @Override
    public ResponseEntity<Product[]> fetchProductListByManufacturer(String manufacturer) {

        List<Product> products_ = productRepository.findByManufacturerContaining(manufacturer);

        if(products_ == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        else
        {
            Product[] products=products_.toArray(new Product[0]);
            return new ResponseEntity<>(products,HttpStatus.OK);
        }
    }

    // find list of products by name
    @Override
    public ResponseEntity<Product[]>  fetchProductListByName(String name) {


        List<Product> products_ = productRepository.findByNameContaining(name);

        if(products_ == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        else
        {
            Product[] products=products_.toArray(new Product[0]);
            return new ResponseEntity<>(products,HttpStatus.OK);
        }

    }


}
