package org.example.CS157A;

import org.example.CS157A.Models.Product;
import org.example.CS157A.Models.Sale;
import org.example.CS157A.Repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public Sale processSale(Sale sale) {
        Product product = productService.getProduct(sale.getProduct().getProductId());

        if (product.getStockQuantity() < sale.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        productService.updateStock(product.getProductId(), -sale.getQuantity());

        sale.setTotalAmount(product.getPrice() * sale.getQuantity());
        sale.setDate(LocalDateTime.now());

        return saleRepository.save(sale);
    }

    public List<Sale> getSalesByDateRange(LocalDateTime start, LocalDateTime end) {
        return saleRepository.findByDateBetween(start, end);
    }

    public List<Sale> searchSales(String search) {
        return saleRepository.searchAllColumns(search);
    }
}