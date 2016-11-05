package de.stuttgart.hdm.csm.pk070.weather;

/**
 * @author patrick.kleindienst
 */
public class WeatherData {

    private String cityName;
    private double currentTemp;
    private int minTemp;
    private int maxTemp;
    private String description;

    public static WeatherData createWith(String cityName, double currentTemp, int minTemp, int maxTemp, String
            description) {
        return new WeatherData(cityName, currentTemp, minTemp, maxTemp, description);
    }

    private WeatherData(String cityName, double currentTemp, int minTemp, int maxTemp, String description) {
        this.cityName = cityName;
        this.currentTemp = currentTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.description = description;
    }

    public String getCityName() {
        return cityName;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public String getDescription() {
        return description;
    }
}
