package de.stuttgart.hdm.csm.pk070;

import de.stuttgart.hdm.csm.pk070.weather.format.WeatherResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author patrick.kleindienst
 */

@Controller
public class WeatherDataConsumerController {

    @Autowired
    private WeatherDataConsumerService consumerService;

    @RequestMapping(value = "/weather", method = RequestMethod.POST)
    public String getWeatherData(@ModelAttribute(value = "zipCode") ZipCodeForm zipCodeForm, Model model) {
        WeatherResponseObject responseObject = consumerService.requestProducerService(zipCodeForm.getZipCode(),
                zipCodeForm.getCountryCode());
        model.addAttribute("result", responseObject);
        return "result";
    }


}
