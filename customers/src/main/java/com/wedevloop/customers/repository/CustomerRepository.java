package com.wedevloop.customers.repository;

import com.wedevloop.customers.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByEmail(String email);
    boolean existsCustomerByEmail(String email);
}
