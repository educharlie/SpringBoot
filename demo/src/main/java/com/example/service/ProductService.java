package com.example.service;

import com.example.model.Product;

import java.util.List;


public interface ProductService {
    List<Product> findAll();
    Product findOne(long id );
    Product create(Product product );
    Product update(Product product );
    void delete(Long id );
    void evitCache();
}
