package com.example.service;


import com.example.model.Product;

import java.util.concurrent.Future;

public interface EmailService {
    Boolean send(Product product);

    void sendAsync(Product product);

    Future<Boolean> sendAsyncWithResult(Product product);
}
