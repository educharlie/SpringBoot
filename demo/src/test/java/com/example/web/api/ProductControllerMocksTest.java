package com.example.web.api;

import com.example.AbstractControllerTest;
import com.example.model.Product;
import com.example.service.EmailService;
import com.example.service.ProductServiceBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
public class ProductControllerMocksTest extends AbstractControllerTest {

    @Mock
    private ProductServiceBean productService;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private ProductController productController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        setUp(productController);
    }

    @Test
    public void testGetProducts() throws Exception {

        // Create some test data
        List<Product> list = getEntityListStubData();

        // Stub the ProductService.findAll method return value
        when(productService.findAll()).thenReturn(list);

        // Perform the behavior being tested
        String uri = "/api/products";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProductService.findAll method was invoked once
        verify(productService, times(1)).findAll();

        // Perform standard JUnit assertions on the response
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

    }

    @Test
    public void testGetProduct() throws Exception {

        // Create some test data
        Long id = new Long(1);
        Product entity = getEntityStubData();

        // Stub the ProductService.findOne method return value
        when(productService.findOne(id)).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/products/{id}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProductService.findOne method was invoked once
        verify(productService, times(1)).findOne(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);
    }

    @Test
    public void testGetProductNotFound() throws Exception {

        // Create some test data
        Long id = Long.MAX_VALUE;

        // Stub the ProductService.findOne method return value
        when(productService.findOne(id)).thenReturn(null);

        // Perform the behavior being tested
        String uri = "/api/products/{id}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProductService.findOne method was invoked once
        verify(productService, times(1)).findOne(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }

    @Test
    public void testCreateProduct() throws Exception {

        // Create some test data
        Product entity = getEntityStubData();

        // Stub the ProductService.create method return value
        when(productService.create(any(Product.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/products";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProductService.create method was invoked once
        verify(productService, times(1)).create(any(Product.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Product createdEntity = super.mapFromJson(content, Product.class);

        Assert.assertNotNull("failure - expected entity not null",
                createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null",
                createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match",
                entity.getName(), createdEntity.getName());
    }

    @Test
    public void testUpdateProduct() throws Exception {

        // Create some test data
        Product entity = getEntityStubData();
        entity.setName(entity.getName() + " test");
        Long id = new Long(1);

        // Stub the ProductService.update method return value
        when(productService.update(any(Product.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/products/{id}";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProductService.update method was invoked once
        verify(productService, times(1)).update(any(Product.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Product updatedEntity = super.mapFromJson(content, Product.class);

        Assert.assertNotNull("failure - expected entity not null",
                updatedEntity);
        Assert.assertEquals("failure - expected id attribute unchanged",
                entity.getId(), updatedEntity.getId());
        Assert.assertEquals("failure - expected text attribute match",
                entity.getName(), updatedEntity.getName());

    }

    @Test
    public void testDeleteProduct() throws Exception {

        // Create some test data
        Long id = new Long(1);

        // Perform the behavior being tested
        String uri = "/api/products/{id}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri, id)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProductService.delete method was invoked once
        verify(productService, times(1)).delete(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }

    private List<Product> getEntityListStubData() {
        List<Product> list = new ArrayList<Product>();
        list.add(getEntityStubData());
        return list;
    }

    private Product getEntityStubData() {
        Product entity = new Product();
        entity.setId(1L);
        entity.setName("Papas");
        return entity;
    }


}
