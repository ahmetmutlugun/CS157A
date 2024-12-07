package org.example.CS157A;

import org.example.CS157A.Product;
import org.example.CS157A.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public Product updateStock(Long productId, Integer quantity) {
        Product product = getProduct(productId);
        product.setStockQuantity(product.getStockQuantity() + quantity);
        return productRepository.save(product);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
