package ca.uoit.group.weather;

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
    private Calendar calendar = Calendar.getInstance();
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
        // Download new weather data
        String callType = "weather";
        String cityId = "6167865";
        String apiKey = "08032fde5fc7affc6aa0333526887cd9";
        String unit = "metric";
        String url = String.format(getString(R.string.api_url), callType, cityId, apiKey, unit);

        DownloadWeatherData weatherDl = new DownloadWeatherData();
        weatherDl.execute(url);
    }

    private void updateWeatherData(WeatherData data) {
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

    public Date getDate() {
        return new Date();
    }

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getSeason() {
        //Winter Spring Summer Autumn
        int result = 0;

        return result;
    }

    // Using org.json.*
    public WeatherData parseWeatherJson(String stringJson) {
        try {
            JSONObject root = new JSONObject(stringJson);
            // Coordinates
            Double lon = root.getJSONObject("coord").getDouble("lon");
            Double lat = root.getJSONObject("coord").getDouble("lat");

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

            // Visibility
            double visibility = root.getDouble("visibility");
            double speed = root.getJSONObject("wind").getDouble("speed");
            double deg = root.getJSONObject("wind").getDouble("deg");
            int clouds = root.getJSONObject("clouds").getInt("all");

            // Time
            int time = root.getInt("dt");
            // Sys
            String country = root.getJSONObject("sys").getString("country");
            int sunrise = root.getJSONObject("sys").getInt("sunrise");
            int sunset = root.getJSONObject("sys").getInt("sunset");

            // City
            int cityId = root.getInt("id");
            String name = root.getString("name");

            WeatherData data = new WeatherData(lon, lat, wId, main, desc, icon, temp, humidity,
                    temp_min, temp_max, visibility, speed, deg, clouds, time, country, sunrise,
                    sunset, name, cityId);

            return data;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private class DownloadWeatherData extends AsyncTask<String, Void, WeatherData> {

        @Override
        protected WeatherData doInBackground(String... params) {
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

                return parseWeatherJson(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(WeatherData data) {
            updateWeatherData(data);
        }
    }

}



