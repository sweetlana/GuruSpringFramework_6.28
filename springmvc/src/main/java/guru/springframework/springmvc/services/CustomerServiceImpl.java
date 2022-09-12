package guru.springframework.springmvc.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import guru.springframework.springmvc.domain.Customer;
import guru.springframework.springmvc.domain.DomainObject;

@Service
public class CustomerServiceImpl extends AbstractMapService implements CustomerService{
	
	@Override
	public List<DomainObject> listAll(){
		return super.listAll();
	}
	
	@Override
	public Customer getById(Integer id) {
		return (Customer) super.getById(id);
	}
	
	@Override
	public void delete(Integer id) {
		super.delete(id);
	} 
	
	@Override
	public Customer saveOrUpdate(Customer domainObject) {
		return (Customer)super.saveOrUpdate(domainObject);
	}
	
	protected void loadDomainObjects() {
		domainMap = new HashMap<>();
		
		Customer customer1 = new Customer();
		customer1.setId(1);
		customer1.setFirstName("Monika");
		customer1.setLastName("Geller");
		customer1.setEmail("geller@gmail.com");
		customer1.setPhoneNumber("1234567");
		customer1.setAddress1("Broad Way, 26");
		customer1.setAddress2("5");
		customer1.setCity("New York City");
		customer1.setState("New York State");
		customer1.setZipCode("10001");
		
		domainMap.put(1, customer1);
		
		Customer customer2 = new Customer();
		customer2.setId(2);
		customer2.setFirstName("Ross");
		customer2.setLastName("Geller");
		customer2.setEmail("geller2@gmail.com");
		customer2.setPhoneNumber("7891234");
		customer2.setAddress1("Broad Way, 26");
		customer2.setAddress2("7");
		customer2.setCity("New York City");
		customer2.setState("New York State");
		customer2.setZipCode("10001");
		
		domainMap.put(2, customer2);
		
		Customer customer3 = new Customer();
		customer3.setId(3);
		customer3.setFirstName("Joe");
		customer3.setLastName("Tribiany");
		customer3.setEmail("tribiany@gmail.com");
		customer3.setPhoneNumber("8912345");
		customer3.setAddress1("Broad Way, 26");
		customer3.setAddress2("6");
		customer3.setCity("New York City");
		customer3.setState("New York State");
		customer3.setZipCode("10001");
		
		domainMap.put(3, customer3);
	}
}
