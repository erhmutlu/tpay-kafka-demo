package org.erhanmutlu.asyncpaymentprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AsyncPaymentProcessor {

    public static void main(String[] args) {
        SpringApplication.run(AsyncPaymentProcessor.class, args);
    }
}
