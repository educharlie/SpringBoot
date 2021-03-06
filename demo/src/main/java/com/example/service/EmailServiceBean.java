package com.example.service;

import java.util.concurrent.Future;

import com.example.model.Product;
import com.example.util.AsyncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * The EmailServiceBean implements all business behaviors defined by the
 * EmailService interface.
 *
 * @author Matt Warman
 */
@Service
public class EmailServiceBean implements EmailService {

    /**
     * The Logger for this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Boolean send(Product product) {
        logger.info("> send");

        Boolean success = Boolean.FALSE;

        // Simulate method execution time
        long pause = 5000;
        try {
            Thread.sleep(pause);
        } catch (Exception e) {
            // do nothing
        }
        logger.info("Processing time was {} seconds.", pause / 1000);

        success = Boolean.TRUE;

        logger.info("< send");
        return success;
    }



    @Async
    @Override
    public void sendAsync(Product product) {
        logger.info("> sendAsync");

        try {
            send(product);
        } catch (Exception e) {
            logger.warn("Exception caught sending asynchronous mail.", e);
        }

        logger.info("< sendAsync");
    }

    @Async
    @Override
    public Future<Boolean> sendAsyncWithResult(Product product) {
        logger.info("> sendAsyncWithResult");

        AsyncResponse<Boolean> response = new AsyncResponse<Boolean>();

        try {
            Boolean success = send(product);
            response.complete(success);
        } catch (Exception e) {
            logger.warn("Exception caught sending asynchronous mail.", e);
            response.completeExceptionally(e);
        }

        logger.info("< sendAsyncWithResult");
        return response;
    }

}
