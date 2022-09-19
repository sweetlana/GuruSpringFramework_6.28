package guru.springframework.springmvc.services;

import java.util.List;

//import org.hamcrest.Matcher;

import guru.springframework.springmvc.domain.Product;

public interface CRUDService<T> {
	List<?> listAll();
	
	T getById(Integer id);
	
	T saveOrUpdate(T domainObject);
	
	void delete(Integer id);
}
