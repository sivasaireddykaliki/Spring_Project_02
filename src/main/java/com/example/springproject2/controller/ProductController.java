package com.example.springproject2.controller;

import com.example.springproject2.entity.Product;
import com.example.springproject2.repository.ProductRepository;
import com.example.springproject2.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    }

    catch (Exception e){

      logger.error("Product not saved");
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // @RequestParam needs to have paramter method exactly as request value in url.

  /*

  Ex: @RequestParam(required = false) String manufacturer gets value  for ?manufacturer=xyz
  @RequestParam(required = false) String parameter gets null for ? manufacturer=xyz because of parameter!=manufacturer
  Hence to handle dynamic request conditions use HttpServletRequest
      for single request parameter with known paramter name use @RequestParam( this should have name as in url)
   */

//  @GetMapping("/productsList")
//  public ResponseEntity<List<Product>> getAllProductsByManufacturer(@RequestParam(required = false) String manufacture){
//
//    List<Product> products = new ArrayList<>();
//
//    products.addAll(productRepository.findByManufacturerContaining(manufacture));
//
//    logger.info("parameter is "+manufacture);
//
//    return  new ResponseEntity<>(products,HttpStatus.OK);
//
//  }


  // Read operation
  @GetMapping("/products")
  public ResponseEntity<Product[]> getproducts(HttpServletRequest request)
  {

      try {

          logger.info("Performing Read Operation: Controller Layer");

          // assuming we pass only 1 parameter. Either manufacturer or name.
          // First access paramter name.

          Set<String> parameterNames=request.getParameterMap().keySet(); // returns set of parameter names. Here we have only 1 parameter
          String parameterName = parameterNames.stream().findFirst().orElse(null);

          // if request object has no parameters
          if(parameterName==null)
          {
          // execute query to return all products without conditions

              return productService.fetchProductList();
          }

          else {

              if (parameterName.equalsIgnoreCase("manufacturer")) {

                  return productService.fetchProductListByManufacturer(request.getParameter(parameterName));
              }
              else if (parameterName.equalsIgnoreCase("name"))
              {

                  return productService.fetchProductListByName(request.getParameter(parameterName));

              }

              // bad request
              else {
                  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
              }
          }
      }

      catch (Exception e) {

          logger.error("Read Operation Unsuccessful");
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  // Read User by Id
  @GetMapping("/products/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable("id") long productId)
  {
      try{
          Optional<Product> product_ = productRepository.findById(productId);
          if(product_.isPresent())
          {
              Product product=product_.get();
              return new ResponseEntity<>(product,HttpStatus.OK);
          }
          else {
              throw new Exception();
          }
      }
      catch (Exception e)
      {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
  }

  // Update operation
  @PutMapping("/products/{id}")
  public ResponseEntity<Product> updateProduct(@RequestBody Product product,@PathVariable("id") long productId)
  {
      try {
          logger.info("Performing Update Operation: Controller Layer");
          return productService.updateProduct(product,productId);
      }
      catch (Exception e) {
          logger.error("Update Operation Unsuccessful");
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

  }

  // Delete opertaion by ID
  @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable("id") long productId)
    {
        try {
            logger.info("Performing Delete Operation: Controller Layer");
            productService.deleteProductById(productId);
            logger.info("Delete Operation Successful");

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            logger.error("Delete Operation Unsuccessful");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
