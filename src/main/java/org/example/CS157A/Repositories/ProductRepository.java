package org.example.CS157A.Repositories;

import org.example.CS157A.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}