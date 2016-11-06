package de.stuttgart.hdm.csm.pk070;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author patrick.kleindienst
 */

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class WeatherDataConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(WeatherDataConsumerApp.class);
    }
}
