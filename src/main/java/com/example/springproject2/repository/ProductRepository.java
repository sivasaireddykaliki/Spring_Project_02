package com.example.springproject2.repository;

import com.example.springproject2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByManufacturerContaining(String manufacturer);


}
