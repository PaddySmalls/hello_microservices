package de.stuttgart.hdm.csm.pk070.weather.format;

import java.time.LocalTime;

/**
 * @author patrick.kleindienst
 */
public class WeatherResponseObject {

    private String cityName;
    private String description;
    private double currentTemp;
    private String time;

    private int responseStatusCode;
    private String message;
    private boolean isCached;

    public static WeatherResponseObject getEmpty() {
        WeatherResponseObject responseObject = new WeatherResponseObject();
        responseObject.setCityName("");
        responseObject.setDescription("");
        responseObject.setCurrentTemp(0.0);
        responseObject.setResponseStatusCode(-1);
        responseObject.setTime(LocalTime.now().toString());
        responseObject.setMessage("Empty response");
        responseObject.setCached(false);
        return responseObject;
    }

    public WeatherResponseObject() {

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean cached) {
        isCached = cached;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public WeatherResponseObject copy() {
        WeatherResponseObject responseObjectCopy = new WeatherResponseObject();
        responseObjectCopy.setCityName(this.getCityName());
        responseObjectCopy.setDescription(this.getDescription());
        responseObjectCopy.setCurrentTemp(this.getCurrentTemp());
        responseObjectCopy.setResponseStatusCode(this.getResponseStatusCode());
        responseObjectCopy.setMessage(this.getMessage());
        responseObjectCopy.setTime(this.getTime());
        return responseObjectCopy;
    }
}
