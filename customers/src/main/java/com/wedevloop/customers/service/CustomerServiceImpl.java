package com.wedevloop.customers.service;

import com.wedevloop.customers.constants.CustomerConstants;
import com.wedevloop.customers.entity.Customer;
import com.wedevloop.customers.repository.CustomerRepository;
import com.wedevloop.customers.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) {

        if (customerRepository.existsCustomerByEmail(customer.getEmail())) {
            throw new RuntimeException(CustomerConstants.EMAIL_JUZ_ISTNIEJE);
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CustomerConstants.CUSTOMER_NOT_FOUND));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CustomerConstants.CUSTOMER_NOT_FOUND));

        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CustomerConstants.CUSTOMER_NOT_FOUND));

        customerRepository.delete(customer);
    }


}
