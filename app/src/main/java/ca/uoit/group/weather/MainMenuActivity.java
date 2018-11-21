package ca.uoit.group.weather;

import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    // Date and Data Holders
    private static LocationManager locationManager;
    private List<WeatherData> weatherDataList = new ArrayList<>();

    // Database helper

    //// Examples
    // Example call: http://api.openweathermap.org/data/2.5/TYPEOFCALL?id=524901&appid=APIKEY
    // http://api.openweathermap.org/data/2.5/weather?id=6167865&appid=08032fde5fc7affc6aa0333526887cd9&units=metric
    // Types of calls:
    //  Current weather API: "weather"
    //  5 days/3 hour forecast API: "forecast"
    //  Weather maps 1.0: "map/{layer}/{z}/{x}/{y}.png?appid={api_key}"
    //  UV index: "uvi"
    //  Weather alerts: "/triggers/{:id}"
    //  60 calls per minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateWeather(null);
    }

    public void updateWeather(View view) {
        updateWeather();
        updateForecast();
    }

    private void updateWeather() {
        // Download new weather data
        updateDataDisplay("weather", "6167865", "metric");
    }

    private void updateForecast() {
        // Download new forecast data
        updateDataDisplay("forecast", "6167865", "metric");
    }

    private void updateDataDisplay(String callType, String cityId, String unit) {
        // Download new JSON
        String apiKey = getString(R.string.api_key);
        String url = String.format(getString(R.string.api_url), callType, cityId, apiKey, unit);
        DownloadJsonData jsonDl = new DownloadJsonData();
        jsonDl.execute(url);
    }

    private void updateWeatherDisplay(WeatherData data) {
        String currTemp = String.format(getString(R.string.temp_celsius), data.getStringTemp());
        String maxTemp = String.format(getString(R.string.temp_celsius), data.getStringMaxTemp());
        String minTemp = String.format(getString(R.string.temp_celsius), data.getStringMinTemp());
        String currDate = getDate().toString();
        String date = currDate.substring(0, currDate.indexOf(" ", 8));
        String fullDate = String.format(getString(R.string.text_last_updated_text), currDate);
        ((TextView)findViewById(R.id.text_curr_temp)).setText(currTemp);
        ((TextView)findViewById(R.id.text_curr_temp_max)).setText(maxTemp);
        ((TextView)findViewById(R.id.text_curr_temp_min)).setText(minTemp);
        ((TextView)findViewById(R.id.text_data_date)).setText(date);
        ((TextView)findViewById(R.id.text_data_last_updated)).setText(fullDate);
    }

    private void updateForecastDisplay(ForecastData data) {
        String day1Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(0).getStringTemp());
        String day2Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(8).getStringTemp());
        String day3Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(17).getStringTemp());
        String day4Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(26).getStringTemp());
        String day5Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(35).getStringTemp());
        ((TextView)findViewById(R.id.text_day1_temp)).setText(day1Temp);
        ((TextView)findViewById(R.id.text_day2_temp)).setText(day2Temp);
        ((TextView)findViewById(R.id.text_day3_temp)).setText(day3Temp);
        ((TextView)findViewById(R.id.text_day4_temp)).setText(day4Temp);
        ((TextView)findViewById(R.id.text_day5_temp)).setText(day5Temp);
    }

    public void changePreferences(View view) {
        startActivity(new Intent(MainMenuActivity.this, PreferencesActivity.class));
    }

    private Date getDate() {
        return new Date();
    }

    private void parseJson(String json) {
        try {
            // Check to see if JSON is current weather data
            updateWeatherDisplay(parseWeatherJson(json, false));
        } catch (JSONException e) {
            System.out.println("Error occurred while parsing forecast Json: " + e.getMessage());
            // JSON is 5-Day forecast
            updateForecastDisplay(parseForecastJson(json));
            System.out.println("Downloaded forecast data");
        }
    }

    private WeatherData parseWeatherJson(String json, boolean isForecast) throws JSONException {
        return parseWeatherJson(new JSONObject(json), isForecast);
    }

    private WeatherData parseWeatherJson(JSONObject root, boolean isForecast) throws JSONException {

        // Weather
        JSONArray arr = root.getJSONArray("weather");
        int wId = arr.getJSONObject(0).getInt("id");
        String main = arr.getJSONObject(0).getString("main");
        String desc = arr.getJSONObject(0).getString("description");
        String icon = arr.getJSONObject(0).getString("icon");

        // Main
        double temp = root.getJSONObject("main").getDouble("temp");
        //int pressure = obj.getJSONObject("main").getInt("pressure");
        int humidity = root.getJSONObject("main").getInt("humidity");
        int temp_min = root.getJSONObject("main").getInt("temp_min");
        int temp_max = root.getJSONObject("main").getInt("temp_max");

        // Wind speeds
        double speed = root.getJSONObject("wind").getDouble("speed");
        double deg = root.getJSONObject("wind").getDouble("deg");
        int clouds = root.getJSONObject("clouds").getInt("all");

        // Time
        int time = root.getInt("dt");

        WeatherData data = null;
        if (!isForecast) {
            // Longitude & Latitude
            Double lon = root.getJSONObject("coord").getDouble("lon");
            Double lat = root.getJSONObject("coord").getDouble("lat");

            double visibility = root.getDouble("visibility");
            String country = root.getJSONObject("sys").getString("country");

            int sunrise = root.getJSONObject("sys").getInt("sunrise");
            int sunset = root.getJSONObject("sys").getInt("sunset");

            // City
            int cityId = root.getInt("id");
            String cityName = root.getString("name");

            data = new WeatherData(lon, lat, wId, main, desc, icon, temp, humidity,
                    temp_min, temp_max, visibility, speed, deg, clouds, time, country, sunrise,
                    sunset, cityName, cityId);
        } else {
            data = new WeatherData(wId, main, desc, icon, temp, humidity,
                    temp_min, temp_max, speed, deg, clouds, time);
        }
        System.out.println("DATA: " + data.toString());
        return data;
    }

    private ForecastData parseForecastJson(String stringJson) {
        try {
            JSONObject root = new JSONObject(stringJson);
            JSONArray forecastJsonArr = root.getJSONArray("list");
            WeatherData[] forecastData = new WeatherData[forecastJsonArr.length()];

            for (int i = 0; i < forecastJsonArr.length(); i++) {
                System.out.println("Forecast: " + forecastJsonArr.getJSONObject(i).toString());
                forecastData[i] = parseWeatherJson(forecastJsonArr.getJSONObject(i), true);
            }

            return new ForecastData(forecastData);
        } catch (JSONException e) {
            System.out.println("Error occurred while parsing forecast Json: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private class DownloadJsonData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                // Connect to the server
                HttpURLConnection conn;
                conn = (HttpURLConnection)url.openConnection();
                int result = conn.getResponseCode();

                // Storing Data
                String line;
                StringBuilder sb = new StringBuilder();

                if (result == HttpURLConnection.HTTP_OK) {
                    // Input stream for HTTP Connection
                    InputStream inStream = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
                    // Build the string for getting price
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                        sb.append('\n');
                        System.out.println(line);
                    }

                    // Close the streams
                    br.close();
                }

                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String json) {
            parseJson(json);
        }
    }


    // Returns the current time of day.
    // Time of a day can be :
    // Night:       12AM->5:99AM
    // Morning:     6AM->11:59AM
    // Afternoon:   12PM->5:59PM
    // Evening:     6PM->11:59PM
    public int getTimeOfDay(){
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        // Night
        if(hour >= 0 && hour < 6){
            return 0;
        }
        // Morning
        if(hour >= 6 && hour < 12){
            return 1;
        }
        // Afternoon
        if(hour >= 12 && hour < 18){
            return 2;
        }
        // Evening
        if(hour >= 18){
            return 3;
        }
        // Error
        return 0;
    }

    // Returns the current season
    // Winter Solstice: December 21
    // Spring Equinox: March 20
    // Summer Solstice: June 21
    // September Equinox: Sept 23
    public int getSeason(){
        int month = Calendar.getInstance().get(Calendar.MONTH);
        // Find out how to parse day later
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int result = 0;

        if(month == Calendar.DECEMBER || month < Calendar.MARCH){
            return 0;
        }
        if(month < Calendar.JUNE){
            return 1;
        }
        if(month < Calendar.SEPTEMBER){
            return 2;
        }
        if(month < Calendar.DECEMBER){
            return 3;
        }
        // Error
        return result;
    }


}



