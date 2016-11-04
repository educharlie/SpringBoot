package com.example.web.api;


import com.example.exception.DefaultExceptionAttributes;
import com.example.exception.ExceptionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleExcpetion(Exception exception, HttpServletRequest httpServletRequest){
        logger.error("> handleException");
        logger.error("> Exception: ", exception);

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, httpServletRequest, HttpStatus.INTERNAL_SERVER_ERROR);

        logger.error("> handleException");
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String,Object>> handleNoResultException(NoResultException exception, HttpServletRequest httpServletRequest){
        logger.error("> handleNoResultException");

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, httpServletRequest, HttpStatus.NOT_FOUND);

        logger.error("> handleNoResultException");
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }
}
