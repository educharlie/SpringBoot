package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Product findOne(long id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product create(Product product) {
        if (product.getId() != null)
        {
            return  null;
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {

        Product productToUpdate = findOne(product.getId());
        if (productToUpdate == null)
            return null;

        productToUpdate.setName(product.getName());
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }
}

