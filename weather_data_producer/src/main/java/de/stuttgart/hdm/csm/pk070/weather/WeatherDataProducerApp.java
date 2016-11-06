package de.stuttgart.hdm.csm.pk070.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author patrick.kleindienst
 *
 * TODO: Is {@link EnableEurekaClient} necessary here?
 */

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class WeatherDataProducerApp {

    public static void main(String[] args) {
        SpringApplication.run(WeatherDataProducerApp.class);
    }
}
