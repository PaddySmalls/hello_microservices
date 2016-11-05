package de.stuttgart.hdm.csm.pk070.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public @ResponseBody String hello() {
        return "This is a test";
    }
}
