package com.example.service;

import com.example.AbstractTest;
import com.example.model.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.List;

@Transactional
public class ProductServiceTest extends AbstractTest {
    @Autowired
    private ProductService productService;

    @Before
    public void setUp(){
        productService.evitCache();
    }

    @After
    public void tearDown(){
        // clean after the test
    }

    @Test
    public void testFindAll(){
        List<Product> products = productService.findAll();

        Assert.assertNotNull("Failure expect not null", products);
        Assert.assertEquals("Failure expected size", 2, products.size());
    }
    @Test
    public void testFindOne() {

        Long id = new Long(1);

        Product entity = productService.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id,
                entity.getId());

    }

    @Test
    public void testFindOneNotFound() {

        Long id = Long.MAX_VALUE;

        Product entity = productService.findOne(id);

        Assert.assertNull("failure - expected null", entity);

    }

    @Test
    public void testCreate() {

        Product entity = new Product();
        entity.setName("test");

        Product createdEntity = productService.create(entity);

        Assert.assertNotNull("failure - expected not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null",
                createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", "test",
                createdEntity.getName());

        List<Product> list = productService.findAll();

        Assert.assertEquals("failure - expected size", 3, list.size());

    }

    @Test
    public void testUpdate() {

        Long id = new Long(1);

        Product entity = productService.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);

        String updatedText = entity.getName() + " test";
        entity.setName(updatedText);
        Product updatedEntity = productService.update(entity);

        Assert.assertNotNull("failure - expected not null", updatedEntity);
        Assert.assertEquals("failure - expected id attribute match", id,
                updatedEntity.getId());
        Assert.assertEquals("failure - expected text attribute match",
                updatedText, updatedEntity.getName());

    }


    @Test
    public void testDelete() {

        Long id = new Long(1);

        Product entity = productService.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);

        productService.delete(id);

        List<Product> list = productService.findAll();

        Assert.assertEquals("failure - expected size", 1, list.size());

        Product deletedEntity = productService.findOne(id);

        Assert.assertNull("failure - expected null", deletedEntity);

    }

}
