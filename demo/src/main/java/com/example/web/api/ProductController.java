package com.example.web.api;


import com.example.model.Product;
import com.example.service.ProductServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController {

    @Autowired
    private ProductServiceBean productServiceBean;

    @RequestMapping(
            path = "/api/products",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productServiceBean.findAll();
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/api/products/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Product> getProduct(@PathVariable long id) {

        Product product = productServiceBean.findOne(id);

        if (product == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @RequestMapping(
            path = "api/products",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Product> createProduct(@RequestBody Product product){

        Product addedProduct = productServiceBean.create(product);
        return new ResponseEntity<Product>(addedProduct, HttpStatus.CREATED);
    }

    @RequestMapping(
            path = "api/products/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        if (product == null){
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Product updatedProduct = productServiceBean.update(product);
        if (updatedProduct == null)
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
    }

    @RequestMapping(
            path = "/api/products/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Product> deleteProduct(@PathVariable long id) {

        productServiceBean.delete(id);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

}
