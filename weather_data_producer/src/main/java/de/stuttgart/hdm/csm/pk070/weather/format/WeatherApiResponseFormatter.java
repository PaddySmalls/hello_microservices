package de.stuttgart.hdm.csm.pk070.weather.format;

import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * @author patrick.kleindienst
 */

@Component
public class WeatherApiResponseFormatter {

    public WeatherResponseObject format(ClientResponse clientResponse) {
        JSONObject jsonObject = getJsonFromResponse(clientResponse);

        WeatherResponseObject weatherResponseObject = new WeatherResponseObject();
        weatherResponseObject.setCityName(extractCityName(jsonObject));
        weatherResponseObject.setDescription(extractTextualDescription(jsonObject));
        weatherResponseObject.setCurrentTemp(extractCurrentTemperature(jsonObject));

        weatherResponseObject.setResponseStatusCode(clientResponse.getStatus());
        weatherResponseObject.setMessage(clientResponse.getStatusInfo().getReasonPhrase());
        weatherResponseObject.setTime(LocalTime.now().toString());

        return weatherResponseObject;
    }

    private JSONObject getJsonFromResponse(ClientResponse response) {
        return new JSONObject(response.getEntity(String.class));
    }

    private String extractCityName(JSONObject jsonResponse) {
        return (String) jsonResponse.get("name");
    }

    private String extractTextualDescription(JSONObject jsonResponse) {
        return (String) ((JSONObject) ((JSONArray) jsonResponse.get("weather")).get(0)).get("description");
    }

    private Double extractCurrentTemperature(JSONObject jsonResponse) {
        return (double) ((JSONObject) jsonResponse.get("main")).get("temp");
    }

}
