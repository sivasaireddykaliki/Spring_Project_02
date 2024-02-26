package com.example.springproject2;

import com.example.springproject2.entity.Product;
import com.example.springproject2.helper.TestHelper;
import com.example.springproject2.repository.ProductRepository;
import com.example.springproject2.service.ProductService;
import com.example.springproject2.service.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class Springproject2ApplicationTests {

	@InjectMocks
	private ProductServiceImpl productService;


	@Mock
	private ProductRepository productRepository;
	private TestHelper testHelper = new TestHelper();

	private List<Product> productsList;

	@Test
	void contextLoads() {

	}
	@BeforeEach
	public void setup(){

		productsList = new ArrayList<>();

		ReflectionTestUtils.setField(productService,"productRepository",productRepository);

		Product product1=testHelper.getDummyProduct(1, "Car", "Sports Car", "Ferrari",true);
		Product product2=testHelper.getDummyProduct(2,"Car","Sports Car","KIA", true);
		Product product3=testHelper.getDummyProduct(3, "Car", "EV", "Hyundai",true);

		productsList.add(product1);
		productsList.add(product2);
		productsList.add(product3);
	}


	@Test
	public void testGetProducts(){

		when(productRepository.findAll()).thenReturn(productsList);

		List<Product> products = Arrays.asList(productService.fetchProductList().getBody());

		Assertions.assertNotNull(products);
	}

	@Test
	public void testSaveProduct(){

		Product new_product = Product.builder().id(4).name("Bike").availability(true).description("Bullet Bike").manufacturer("Royal Enfield").build();

		// productsList.add(new_product);

		when(productRepository.save(new_product)).thenReturn(new_product);

		Product product =  productService.saveProduct(new_product);

		Assertions.assertNotNull(product);
		Assertions.assertEquals(product,new_product);
	}

	@Test
	public void testUpdateProductById(){

		Product updated_product = Product.builder().id(1).name("Car").availability(true).description("EV").manufacturer("Tesla").build();

		long idval = updated_product.getId();



		// find if new product id value matches with list of products
		when(productRepository.findById(idval)).thenReturn(productsList.stream().filter(product$ -> product$.getId() == idval).findFirst());
		when(productRepository.save(updated_product)).thenReturn(updated_product);

		Product product_  = productService.updateProduct(updated_product, updated_product.getId()).getBody();


		Assertions.assertNotNull(product_);
		Assertions.assertTrue(product_.getManufacturer() != "Ferrari" && product_.getManufacturer().equalsIgnoreCase("Tesla"));


		// Product product = productService.updateProduct(product1, product1.getId()).getBody();

		// Assertions.assertNotNull(product);
	}

//	@Test
//	public void testDeleteProductById(){
//
//		long idVal=3;
//
//		int index = (int)idVal-1;
//
//		doNothing().when(productRepository).deleteById(idVal);
//
//		productService.deleteProductById(idVal);
//
//		Product result = productsList.remove(index);
//
//		System.out.println(productsList);
//
//		Assertions.assertNotNull(result);
//
//
//	}
}
