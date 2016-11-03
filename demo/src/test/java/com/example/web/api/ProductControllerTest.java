package com.example.web.api;

import com.example.AbstractControllerTest;
import com.example.model.Product;
import com.example.service.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ProductControllerTest extends AbstractControllerTest {

    @Autowired
    private ProductService productService;

    @Before
    public void setUp() {
        super.setUp();
        productService.evitCache();
    }

    @Test
    public void testGetProducts() throws Exception {

        String uri = "/api/products";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

    }

    @Test
    public void testGetProduct() throws Exception {

        String uri = "/api/products/{id}";
        Long id = new Long(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

    }

    @Test
    public void testGetProductNotFound() throws Exception {

        String uri = "/api/products/{id}";
        Long id = Long.MAX_VALUE;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }

    @Test
    public void testCreateProduct() throws Exception {

        String uri = "/api/products";
        Product product = new Product();
        product.setName("test");
        String inputJson = super.mapToJson(product);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Product createdProduct = super.mapFromJson(content, Product.class);

        Assert.assertNotNull("failure - expected product not null",
                createdProduct);
        Assert.assertNotNull("failure - expected product.id not null",
                createdProduct.getId());
        Assert.assertEquals("failure - expected product.text match", "test",
                createdProduct.getName());

    }

    @Test
    public void testUpdateProduct() throws Exception {

        String uri = "/api/products/{id}";
        Long id = new Long(1);
        Product product = productService.findOne(id);
        String updatedText = product.getName() + " test";
        product.setName(updatedText);
        String inputJson = super.mapToJson(product);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Product updatedProduct = super.mapFromJson(content, Product.class);

        Assert.assertNotNull("failure - expected product not null",
                updatedProduct);
        Assert.assertEquals("failure - expected product.id unchanged",
                product.getId(), updatedProduct.getId());
        Assert.assertEquals("failure - expected updated product text match",
                updatedText, updatedProduct.getName());

    }

    @Test
    public void testDeleteProduct() throws Exception {

        String uri = "/api/products/{id}";
        Long id = new Long(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri, id)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

        Product deletedProduct = productService.findOne(id);

        Assert.assertNull("failure - expected product to be null",
                deletedProduct);

    }


}
