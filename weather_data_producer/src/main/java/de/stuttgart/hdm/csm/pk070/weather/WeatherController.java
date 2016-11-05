package de.stuttgart.hdm.csm.pk070.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author patrick.kleindienst
 */

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/weather/city/{id}")
    public @ResponseBody WeatherData weather(@PathVariable("id") String cityId) {
        return weatherService.getDataByCityId(cityId);
    }


}
