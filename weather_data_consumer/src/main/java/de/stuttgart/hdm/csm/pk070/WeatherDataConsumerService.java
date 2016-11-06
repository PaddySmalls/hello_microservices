package de.stuttgart.hdm.csm.pk070;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.stuttgart.hdm.csm.pk070.weather.cache.WeatherDataCache;
import de.stuttgart.hdm.csm.pk070.weather.format.WeatherResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author patrick.kleindienst
 */

@Service
public class WeatherDataConsumerService {

    private static final String PRODUCER_SERVICE_ID = "producer";

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private WeatherDataCache weatherDataCache;

    @HystrixCommand(fallbackMethod = "producerRequestFallback")
    public WeatherResponseObject requestProducerService(String zipCode, String countryCode) {
        ServiceInstance producerInstance = loadBalancerClient.choose(PRODUCER_SERVICE_ID);

        WeatherResponseObject response = new RestTemplate().getForObject(buildURI(producerInstance),
                WeatherResponseObject.class, toRequestParamMap(zipCode, countryCode));

        weatherDataCache.store(zipCode, response);

        return response;
    }

    public WeatherResponseObject producerRequestFallback(String zipCode, String countryCode) {
        WeatherResponseObject responseObject = weatherDataCache.lookup(zipCode).orElse(
                WeatherResponseObject.getEmpty());
        if (responseObject.isCached()) {
            responseObject.setMessage("CAUTION - Response out of date, producer unreachable!");
        }
        return responseObject;
    }

    private String buildURI(ServiceInstance instance) {
        return String.format("http://%s:%s/weather/?zipCode={zipCode}&countryCode={countryCode}", instance.getHost(),
                instance.getPort());
    }

    private Map<String, String> toRequestParamMap(String zipCode, String countryCode) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("zipCode", zipCode);
        paramMap.put("countryCode", countryCode);
        return paramMap;
    }
}
