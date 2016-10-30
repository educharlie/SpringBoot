package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceBean implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Cacheable(
            value = "products",
            key = "#id"
    )
    public Product findOne(long id) {
        return productRepository.findOne(id);
    }

    @Override
    @CachePut(
            value = "products",
            key = "#result.id"
    )
    public Product create(Product product) {
        if (product.getId() != null) {
            return  null;
        }

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    @CachePut(
            value = "products",
            key = "#product.id"
    )
    public Product update(Product product) {

        Product productToUpdate = findOne(product.getId());
        if (productToUpdate == null)
            return null;

        productToUpdate.setName(product.getName());
        return productRepository.save(product);
    }

    @Override
    @CacheEvict(
            value = "products",
            key = "#id"
    )
    public void delete(Long id) {
        productRepository.delete(id);
    }

    @Override
    @CacheEvict(
            value = "products",
            allEntries = true
    )
    public void evitCache(){

    }
}

