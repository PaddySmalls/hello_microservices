package de.stuttgart.hdm.csm.pk070.weather.cache;

import de.stuttgart.hdm.csm.pk070.weather.format.WeatherResponseObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author patrick.kleindienst
 */

@Component
public class WeatherDataCache {

    private Map<String, WeatherResponseObject> responseCache = new HashMap<>();


    public void store(String key, WeatherResponseObject responseObject) {
        WeatherResponseObject responseObjectCopy = responseObject.copy();
        responseObjectCopy.setCached(true);
        responseCache.put(key, responseObjectCopy);
    }

    public Optional<WeatherResponseObject> lookup(String zipCode) {
        if (responseCache.containsKey(zipCode) && Objects.nonNull(responseCache.get(zipCode))) {
            return Optional.of(responseCache.get(zipCode));
        } else {
            return Optional.empty();
        }
    }
}
