package com.example.actuator.health;

import com.example.model.Product;
import com.example.service.ProductServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductHealthIndicator implements HealthIndicator {

    @Autowired
    private ProductServiceBean productService;

    @Override
    public Health health() {
        List<Product> products = productService.findAll();
        if (products == null || products.size() == 0) {
            return Health.down().withDetail("count", 0).build();
        }

        return Health.up().withDetail("count", products.size()).build();
    }
}
