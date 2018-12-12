package ca.uoit.group.weather;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    private static final int[] FORECAST_INDEX = { 0, 8, 17, 26, 35 };
    private ForecastData currentForecast;
    private WeatherData currentWeatherData;
    WeatherReceiver receiver = new WeatherReceiver();
    WeatherDBHelper weatherDBHelper;
    List<LocationData> locations;
    LocationDBHelper locationDBHelper;
    private int interval = 1;//1min notification

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

        // Initialize db helpers
        locationDBHelper = new LocationDBHelper(getApplicationContext());

//
//            createNotificationChannel();
//            IntentFilter filter = new IntentFilter(Intent.ACTION_DEFAULT); //IDK WHAT TO DO
//            registerReceiver(receiver, filter);



    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get locations
        locations = locationDBHelper.getAllLocations();

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

        try {
            // Set current weather icon
            String iconName = data.getIcon() + ".png";
            Drawable icon = Drawable.createFromStream(getAssets().open(iconName), null);
            ((ImageView)findViewById(R.id.icon_curr_weather)).setImageDrawable(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateForecastDisplay(ForecastData data) {
        String day1Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(FORECAST_INDEX[0]).getStringTemp());
        String day2Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(FORECAST_INDEX[1]).getStringTemp());
        String day3Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(FORECAST_INDEX[2]).getStringTemp());
        String day4Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(FORECAST_INDEX[3]).getStringTemp());
        String day5Temp = String.format(getString(R.string.temp_celsius),
                data.getWeatherData(FORECAST_INDEX[4]).getStringTemp());

        ((TextView)findViewById(R.id.text_day1_temp)).setText(day1Temp);
        ((TextView)findViewById(R.id.text_day2_temp)).setText(day2Temp);
        ((TextView)findViewById(R.id.text_day3_temp)).setText(day3Temp);
        ((TextView)findViewById(R.id.text_day4_temp)).setText(day4Temp);
        ((TextView)findViewById(R.id.text_day5_temp)).setText(day5Temp);

        try {
            // Set 5-day forecast weather icons
            String icon1Name = data.getWeatherData(FORECAST_INDEX[0]).getIcon() + ".png";
            Drawable icon1 = Drawable.createFromStream(getAssets().open(icon1Name), null);
            ((ImageView)findViewById(R.id.icon_day1_weather)).setImageDrawable(icon1);

            String icon2Name = data.getWeatherData(FORECAST_INDEX[1]).getIcon() + ".png";
            Drawable icon2 = Drawable.createFromStream(getAssets().open(icon2Name), null);
            ((ImageView)findViewById(R.id.icon_day2_weather)).setImageDrawable(icon2);

            String icon3Name = data.getWeatherData(FORECAST_INDEX[2]).getIcon() + ".png";
            Drawable icon3 = Drawable.createFromStream(getAssets().open(icon3Name), null);
            ((ImageView)findViewById(R.id.icon_day3_weather)).setImageDrawable(icon3);

            String icon4Name = data.getWeatherData(FORECAST_INDEX[3]).getIcon() + ".png";
            Drawable icon4 = Drawable.createFromStream(getAssets().open(icon4Name), null);
            ((ImageView)findViewById(R.id.icon_day4_weather)).setImageDrawable(icon4);

            String icon5Name = data.getWeatherData(FORECAST_INDEX[4]).getIcon() + ".png";
            Drawable icon5 = Drawable.createFromStream(getAssets().open(icon5Name), null);
            ((ImageView)findViewById(R.id.icon_day5_weather)).setImageDrawable(icon5);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            // JSON is 5-Day forecast
            updateForecastDisplay(parseForecastJson(json));
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
        double temp_min = root.getJSONObject("main").getDouble("temp_min");
        double temp_max = root.getJSONObject("main").getDouble("temp_max");

        // Wind speeds
        double speed = root.getJSONObject("wind").getDouble("speed");
        double deg = root.getJSONObject("wind").getDouble("deg");
        int clouds = root.getJSONObject("clouds").getInt("all");

        // Time
        int time = root.getInt("dt");
        String time_text = "";
        if (isForecast) {
            // Get update time for 5-day forecast
            time_text = root.getString("dt_txt");
        }

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

            locationDBHelper.insertLocation(cityName, cityId, country, lat, lon);
        } else {
            data = new WeatherData(wId, main, desc, icon, temp, humidity,
                    temp_min, temp_max, speed, deg, clouds, time, time_text);
        }
        currentWeatherData = data;

        return data;
    }

    private ForecastData parseForecastJson(String stringJson) {
        try {
            JSONObject root = new JSONObject(stringJson);
            JSONArray forecastJsonArr = root.getJSONArray("list");
            WeatherData[] forecastData = new WeatherData[forecastJsonArr.length()];

            for (int i = 0; i < forecastJsonArr.length(); i++) {
                forecastData[i] = parseWeatherJson(forecastJsonArr.getJSONObject(i), true);
            }

            ForecastData result = new ForecastData(forecastData);
            currentForecast = result;
            return result;
        } catch (JSONException e) {
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
                        //System.out.println(line);
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

    public void openGraph(View view){
        Intent i = new Intent(this, GraphActivity.class);
        i.putExtra("data",currentForecast);
        startActivity(i);
    }

;


//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Weather Owl";
//            String description = "Updated Weather";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("1234", name,NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    @Override
//    public void onStop()
//    {
//        super.onStop();
//        if(receiver !=null)
//            unregisterReceiver(receiver);
//
//    }

}



