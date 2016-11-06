package de.stuttgart.hdm.csm.pk070.weather.api;

import de.stuttgart.hdm.csm.pk070.weather.format.WeatherResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author patrick.kleindienst
 */

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/weather")
    public @ResponseBody WeatherResponseObject weather(@RequestParam("zipCode") String zipCode,
                                  @RequestParam(value = "countryCode", required = false) String countryCode) {
        return weatherService.getWeatherDataByZipCodeAndCountry(zipCode, countryCode);
    }

}
