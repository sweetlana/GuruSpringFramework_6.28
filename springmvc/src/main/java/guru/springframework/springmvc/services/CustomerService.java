package guru.springframework.springmvc.services;

import java.util.List;

import guru.springframework.springmvc.domain.Customer;

public interface CustomerService {

	List<Customer> listAllCustomers();
	
	Customer getCustomerById(Integer id);
	
	Customer saveOrUpdateCustomer(Customer customer);
	
	void deleteCustomer(Integer id);
}
