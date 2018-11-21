package ca.uoit.group.weather;

import java.io.Serializable;

public class ForecastData implements Serializable {

    WeatherData[] data;

    public ForecastData(WeatherData[] forecastData) {
        this.data = forecastData;
    }

    public int getForecastSize() {
        return data.length;
    }

    public WeatherData getWeatherData(int index) {
        return data[index];
    }

    public void setWeatherData(int index, WeatherData data) {
        this.data[index] = data;
    }
}
