package org.example.CS157A.Repositories;

import  org.example.CS157A.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}