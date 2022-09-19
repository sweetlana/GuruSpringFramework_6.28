package guru.springframework.springmvc.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentMatchers;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*; //hasSize, instanceOf
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import guru.springframework.springmvc.domain.Product;
import guru.springframework.springmvc.services.ProductService;

public class ProductControllerTest {
	
	@Mock //Mockito Mock object
	private ProductService productService;
	
	@InjectMocks //setups up controller? and inject mock objects into it.
	private ProductController productController;
	
	private MockMvc mockMvc;
	

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this); //initializes controller and mocks
		
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void testList() throws Exception{
		
		List<Product> products = new ArrayList<>();
		products.add(new Product());
		products.add(new Product());
		
		//specific Mockito interaction, tell stub to return list of products
		when(productService.listAll()).thenReturn((List) products); //need to strip generics to keep Mockito happy 
		
		mockMvc.perform(get("/product/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("product/list"))
				.andExpect(model().attribute("products", hasSize(2)));
	}
	
	@Test
	public void testShow() throws Exception{
		Integer id = 1;
		
		//Tell Mockito stub to return new product for ID 1
		when(productService.getById(id)).thenReturn(new Product());
		
		mockMvc.perform(get("/product/show/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("product/show"))
				.andExpect(model().attribute("product", instanceOf(Product.class)));
	}

	@Test
	public void testEdit() throws Exception{
		Integer id = 1;
		
		//Tell Mockito stub to return new product for ID 1
		when(productService.getById(id)).thenReturn(new Product());
		
		mockMvc.perform(get("/product/edit/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("product/productform"))
				.andExpect(model().attribute("product", instanceOf(Product.class)));
	}
	
	@Test
	public void testNewProduct() throws Exception {
		Integer id = 1;
		
		//should not call service
		//verifyZeroInteractions(productService); //deprecated
		verifyNoInteractions(productService);
		
		mockMvc.perform(get("/product/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("product/productform"))
				.andExpect(model().attribute("product", instanceOf(Product.class)));
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		Integer id = 1;
		String description = "Test Description";
		BigDecimal price = new BigDecimal("12.00");
		String imageUrl = "example.com";
		
		Product returnProduct = new Product();
		returnProduct.setId(id);
		returnProduct.setDescription(description);
		returnProduct.setPrice(price);
		returnProduct.setImageUrl(imageUrl);
		
		//when(productService.saveOrUpdate(Matchers.<Product>any(null))).thenReturn(returnProduct);
		//when(productService.saveOrUpdate(Matchers.<Product>any())).thenReturn(returnProduct);
		//when(productService.saveOrUpdate((Product) Matchers.<Product>any(Product.class))).thenReturn(returnProduct);
		when(productService.saveOrUpdate((Product)org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(returnProduct);
		
		
		mockMvc.perform(post("/product")
				.param("id", "1")
				.param("description", description)
				.param("price", "12.00")
				.param("imageUrl", "example.com"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/product/show/1"))
					.andExpect(model().attribute("product", instanceOf(Product.class)))
					.andExpect(model().attribute("product", hasProperty("id", is(id))))
					.andExpect(model().attribute("product", hasProperty("description", is(description))))
					.andExpect(model().attribute("product", hasProperty("price", is(price))))
					.andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));
		
		//verify properties of bound object
		ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
		verify(productService).saveOrUpdate(boundProduct.capture());
		
		assertEquals(id, boundProduct.getValue().getId());
		assertEquals(description, boundProduct.getValue().getDescription());
		assertEquals(price, boundProduct.getValue().getPrice());
		assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
	}
	
	@Test
	public void testDelete() throws Exception{
		Integer id = 1;
		
		mockMvc.perform(get("/product/delete/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/product/list"));
		
		verify(productService, times(1)).delete(id);
	}
}
