package guru.springframework.springmvc.services;

import java.util.List;

import guru.springframework.springmvc.domain.Product;

public interface ProductService {
	
	List<Product> listAllProducts();
}
