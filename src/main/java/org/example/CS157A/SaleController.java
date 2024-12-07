package org.example.CS157A;

import org.example.CS157A.Sale;
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
}