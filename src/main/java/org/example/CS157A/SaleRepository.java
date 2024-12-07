package org.example.CS157A;

import org.example.CS157A.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByDateBetween(LocalDateTime start, LocalDateTime end);
}