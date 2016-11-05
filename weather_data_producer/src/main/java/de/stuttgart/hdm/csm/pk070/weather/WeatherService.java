package de.stuttgart.hdm.csm.pk070.weather;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author patrick.kleindienst
 */

@Service
public class WeatherService {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String APPID = "APPID=???";

    @HystrixCommand(fallbackMethod = "weatherFallback")
    public WeatherData getDataByCityId(String cityId) {


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Client client = Client.create();
        WebResource webResource = client.resource(BASE_URL + "?id=" + cityId + "&units=metric&" + APPID);

        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        WeatherData weatherData = null;

        if (response.getStatus() == 200) {
            String data = response.getEntity(String.class);
            System.out.println(data);
            JSONObject jsonObject = new JSONObject(data);


            String city = extractCityNameFrom(jsonObject);
            double currentTemp = extractCurrentTempFrom(jsonObject);
            int minTemp = extractMinTempFrom(jsonObject);
            int maxTemp = extractMaxTempFrom(jsonObject);
            String description = extractDescriptionFrom(jsonObject);

            weatherData = WeatherData.createWith(city, currentTemp, minTemp, maxTemp, description);
        } else {
            // throw Exception >> jump to fallback
        }

        return weatherData;
    }

    private String extractDescriptionFrom(JSONObject jsonObject) {
        return (String) ((JSONObject) ((JSONArray) jsonObject.get("weather")).get(0)).get("description");
    }

    private int extractMaxTempFrom(JSONObject jsonObject) {
        return (int) ((JSONObject) jsonObject.get("main")).get("temp_max");
    }

    private String extractCityNameFrom(JSONObject jsonObject) {
        return (String) jsonObject.get("name");
    }

    private double extractCurrentTempFrom(JSONObject jsonObject) {
        return (double) ((JSONObject) jsonObject.get("main")).get("temp");
    }

    private int extractMinTempFrom(JSONObject jsonObject) {
        return (int) ((JSONObject) jsonObject.get("main")).get("temp_min");
    }


    public WeatherData weatherFallback(String cityId) {
        return WeatherData.createWith("Stuttgart", 0.0, 0, 0, "ERROR");
    }
}
