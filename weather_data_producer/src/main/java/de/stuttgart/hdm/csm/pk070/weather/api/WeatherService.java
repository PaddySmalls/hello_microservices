package de.stuttgart.hdm.csm.pk070.weather.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import de.stuttgart.hdm.csm.pk070.weather.cache.WeatherDataCache;
import de.stuttgart.hdm.csm.pk070.weather.exception.BadApiResponseException;
import de.stuttgart.hdm.csm.pk070.weather.format.WeatherApiResponseFormatter;
import de.stuttgart.hdm.csm.pk070.weather.format.WeatherResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.Objects;

/**
 * @author patrick.kleindienst
 */

@Service
class WeatherService {

    private static final String BASE_URI = "http://api.openweathermap.org/data/2.5/weather";

    @Autowired
    private ApiKeyReader apiKeyReader;

    @Autowired
    private WeatherApiResponseFormatter responseFormatter;

    @Autowired
    private WeatherDataCache weatherDataCache;

    private String appID;


    @HystrixCommand(fallbackMethod = "weatherApiFallback")
    public WeatherResponseObject getWeatherDataByZipCodeAndCountry(String zipCode, String countryCode) {

        URI openWeatherMapURI = buildUriWithRequestParams(zipCode, countryCode);
        WebResource webResource = prepareWebResource(openWeatherMapURI);
        ClientResponse clientResponse = dispatchRequest(webResource);

        if (isResponseOk(clientResponse)) {
            WeatherResponseObject result = responseFormatter.format(clientResponse);
            weatherDataCache.store(zipCode, result);
            return result;
        } else {
            throw new BadApiResponseException(String.format("Bad response: %s (%d)", clientResponse.getStatusInfo()
                    .getReasonPhrase(), clientResponse.getStatus()));
        }
    }

    public WeatherResponseObject weatherApiFallback(String zipCode, String countryCode) {
        WeatherResponseObject responseObject = weatherDataCache.lookup(zipCode).orElse(
                WeatherResponseObject.getEmpty());
        if (responseObject.isCached()) {
            responseObject.setMessage("CAUTION - Response out of date!");
        }
        return responseObject;
    }


    private URI buildUriWithRequestParams(String zipCode, String countryCode) {
        return URI.create(String.format(BASE_URI + "?zip=%s,%s&units=metric&APPID=%s", zipCode, Objects.nonNull
                (countryCode) ? countryCode : "", getApiKey()));
    }

    private String getApiKey() {
        if (Objects.isNull(appID)) {
            appID = apiKeyReader.readKey();
        }
        return appID;
    }

    private WebResource prepareWebResource(URI targetURI) {
        Client client = Client.create();
        return client.resource(targetURI);
    }

    private ClientResponse dispatchRequest(WebResource webResource) {
        return webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
    }

    private boolean isResponseOk(ClientResponse clientResponse) {
        return Objects.nonNull(clientResponse) && ClientResponse.Status.OK.getStatusCode() == clientResponse
                .getStatusInfo().getStatusCode();
    }


}
