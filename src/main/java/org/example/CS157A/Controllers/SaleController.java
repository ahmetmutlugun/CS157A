package org.example.CS157A.Controllers;

import org.example.CS157A.Repositories.SaleRepository;
import org.example.CS157A.Models.Sale;
import org.example.CS157A.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
    @Autowired
    private SaleService saleService;
    @Autowired
    private SaleRepository saleRepository;

    @PostMapping
    public Sale processSale(@RequestBody Sale sale) {
        return saleService.processSale(sale);
    }

    @GetMapping
    public List<Sale> getSalesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return saleService.getSalesByDateRange(start, end);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Long id) {
        saleRepository.deleteById(id);
    }
}