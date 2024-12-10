package org.example.CS157A.Repositories;

import org.example.CS157A.Models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {
    List<Sale> findByDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Sale s WHERE CONCAT(" +
            "s.saleId, ' ', " +
            "s.date, ' ', " +
            "s.product.name, ' ', " +
            "s.customer.name, ' ', " +
            "s.quantity, ' ', " +
            "s.totalAmount) LIKE %:searchTerm%")
    List<Sale> searchAllColumns(@Param("searchTerm") String searchTerm);
}