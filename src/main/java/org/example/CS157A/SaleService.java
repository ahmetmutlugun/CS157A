package org.example.CS157A;

import org.example.CS157A.Sale;
import org.example.CS157A.Product;
import org.example.CS157A.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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

        // Update stock
        productService.updateStock(product.getProductId(), -sale.getQuantity());

        // Calculate total amount
        sale.setTotalAmount(product.getPrice() * sale.getQuantity());
        sale.setDate(LocalDateTime.now());

        return saleRepository.save(sale);
    }

    public List<Sale> getSalesByDateRange(LocalDateTime start, LocalDateTime end) {
        return saleRepository.findByDateBetween(start, end);
    }
}
