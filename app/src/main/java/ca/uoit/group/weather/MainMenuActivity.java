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
import android.text.Editable;
import android.text.TextWatcher;
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
    WeatherReceiver receiver = new WeatherReceiver();
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
        updateWeathers(null);
    }

    // Converts celsius to fahrenheit
    private float celciusToFar(float celsius) {
        return ((celsius * 9) / 5) + 32;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Get locations
        locations = locationDBHelper.getAllLocations();
    }

    public void updateWeathers(View view) {
        updateWeather("6167865");
        updateForecast("6167865");
    }

    private void updateWeather(String city_id) {
        // Download new weather data
        updateDataDisplay("weather", city_id, "metric");
    }

    private void updateForecast(String city_id) {
        // Download new forecast data
        updateDataDisplay("forecast", city_id, "metric");
    }

    private void updateDataDisplay(String callType, String cityId, String unit) {
        // Download new JSON
        String apiKey = getString(R.string.api_key);
        String url = String.format(getString(R.string.api_url), callType, cityId, apiKey, unit);
        DownloadJsonData jsonDl = new DownloadJsonData();
        jsonDl.execute(url, "weather");
    }

    private void updateWeatherDisplay(WeatherData data) {
        String currTemp = String.format(getString(R.string.temp_celsius), data.getStringTemp());
        String maxTemp = String.format(getString(R.string.temp_celsius), data.getStringMaxTemp());
        String minTemp = String.format(getString(R.string.temp_celsius), data.getStringMinTemp());
        String currDate = getDate().toString();
        String date = currDate.substring(0, currDate.indexOf(" ", 8));
        String fullDate = String.format(getString(R.string.text_last_updated_text), currDate);

        ((TextView)findViewById(R.id.text_curr_temp)).setText(currTemp);
        ((TextView)findViewById(R.id.text_curr_city)).setText(data.getCityName());
        ((TextView)findViewById(R.id.text_curr_temp_max)).setText(maxTemp);
        ((TextView)findViewById(R.id.text_curr_temp_min)).setText(minTemp);
        ((TextView)findViewById(R.id.text_data_date)).setText(date);
        ((TextView)findViewById(R.id.text_data_last_updated)).setText(fullDate);

        // Set weather condition text
        ((TextView)findViewById(R.id.text_curr_weather)).setText(data.getType());
        // Set weather description if not same
        if (data.getType().toLowerCase().equals(data.getDesc().toLowerCase())) {
            ((TextView) findViewById(R.id.text_curr_weather_desc)).setText("");
        } else {
            ((TextView) findViewById(R.id.text_curr_weather_desc)).setText(data.getDesc());
        }

        try {
            // Set current weather icon
            String iconFileName = data.getIcon() + ".png";
            Drawable icon = Drawable.createFromStream(getAssets().open(iconFileName), null);
            ((ImageView)findViewById(R.id.icon_curr_weather)).setImageDrawable(icon);

            // Set background image based on current weather conditions
            Drawable bgFile = getDrawable(getResources().getIdentifier(data.getType().toLowerCase(),
                    "drawable", getPackageName()));
            ((ImageView)findViewById(R.id.image_weather_bg)).setImageDrawable(bgFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {}
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

        // Set 5-day forecast
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DATE, true);
        ((TextView)findViewById(R.id.text_day1_temp)).setText(day1Temp);
        ((TextView)findViewById(R.id.text_day1_day)).setText(getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)));

        cal.roll(Calendar.DATE, true);
        ((TextView)findViewById(R.id.text_day2_temp)).setText(day2Temp);
        ((TextView)findViewById(R.id.text_day2_day)).setText(getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)));

        cal.roll(Calendar.DATE, true);
        ((TextView)findViewById(R.id.text_day3_temp)).setText(day3Temp);
        ((TextView)findViewById(R.id.text_day3_day)).setText(getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)));

        cal.roll(Calendar.DATE, true);
        ((TextView)findViewById(R.id.text_day4_temp)).setText(day4Temp);
        ((TextView)findViewById(R.id.text_day4_day)).setText(getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)));

        cal.roll(Calendar.DATE, true);
        ((TextView)findViewById(R.id.text_day5_temp)).setText(day5Temp);
        ((TextView)findViewById(R.id.text_day5_day)).setText(getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)));

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
            // Weather icon not found
            e.printStackTrace();
        }
    }

    public void changePreferences(View view) {
        startActivity(new Intent(MainMenuActivity.this, PreferencesActivity.class));
    }

    private Date getDate() {
        return new Date();
    }

    private void setSuggestions() {
        final String sessionToken = String.valueOf(getDate().getTime());
        final DownloadJsonData jsonDownloader = new DownloadJsonData();
        /*
        yourEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String url = String.format(getString(R.string.city_search_api_url), s.toString(), getString(R.string.places_api_key), sessionToken);
                System.out.println(url);
                jsonDownloader.execute(url, "cities");
            }
        });
        */
    }

    public void setSuggestions(String json) {
        try {
            JSONObject root = new JSONObject(json);

            // Parse the JSON and set the top 10 as suggestions
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJson(String json, String jsonType) {
        if (jsonType.equals("weather")) {
            try {
                // Check to see if JSON is current weather data
                updateWeatherDisplay(parseWeatherJson(json, false));
            } catch (JSONException e) {
                // JSON is 5-Day forecast
                updateForecastDisplay(parseForecastJson(json));
            }
        } else if (jsonType.equals("cities")) {

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

        String jsonType;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                jsonType = params[1];
                // Connect to the server
                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
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
            parseJson(json, jsonType);
        }
    }

    private String getDayOfWeek(int day) {
        switch(day - 1) {
            case 1: return "Mon";
            case 2: return "Tue";
            case 3: return "Wed";
            case 4: return "Thu";
            case 5: return "Fri";
            case 6: return "Sat";
            case 0: return "Sun";
        }
        return "";
    }

    public String getSeason(){
        int month = Calendar.getInstance().get(Calendar.MONTH);
        // Find out how to parse day later
        if(month == Calendar.DECEMBER || month < Calendar.MARCH){
            return "Winter";
        }
        if(month < Calendar.JUNE){
            return "Spring";
        }
        if(month < Calendar.SEPTEMBER){
            return "Summer";
        }
        if(month < Calendar.DECEMBER){
            return "Autumn";
        }
        // Error
        return "";
    }

    public void openGraph(View view){
        Intent i = new Intent(this, GraphActivity.class);
        i.putExtra("data",currentForecast);
        startActivity(i);
    }

    public void openAnalysis(View view){
        Intent i = new Intent(this, AnalysisActivity.class);
        i.putExtra("data",currentForecast);
        startActivity(i);
    }

    public void openSearch(View view){
        Intent i = new Intent(this, SearchActivity.class);
        startActivityForResult(i,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("id");
                System.out.println(result);
                updateWeather(result);
                updateForecast(result);
            }
        }
    }


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