package ca.uoit.group.weather;

public class ForecastData {

    WeatherData[] data;

    public ForecastData(WeatherData[] forecastData) {
        this.data = forecastData;
    }

    public WeatherData getWeatherData(int index) {
        return data[index];
    }

    public void setWeatherData(int index, WeatherData data) {
        this.data[index] = data;
    }
}
