package ca.uoit.group.weather;

import org.json.*;
import android.Manifest;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class Main_Menu extends AppCompatActivity {
    // Key
    private static final String API_KEY = "08032fde5fc7affc6aa0333526887cd9";

    // Permissions
    private static final String FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String INTERNET = Manifest.permission.INTERNET;

    // Date and Data Holders
    private Calendar calendar = Calendar.getInstance();
    private ArrayList<Location> locationList = new ArrayList<>();
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
        setContentView(R.layout.activity_main__menu);

        TextView time = findViewById(R.id.time);
        TextView api_key = findViewById(R.id.api_key);
        api_key.setText(API_KEY);
        time.setText(getDate().toString());

    }

    public void findCurrentLocation(){

    }


    public void getData(View view){
        // TYPE OF CALL
        String TYPE_OF_CALL = "weather";
        // CITY ID (Toronto)
        String ID = "6167865";
        // URL to get DATA
        String URL = "http://api.openweathermap.org/data/2.5/"+TYPE_OF_CALL+"?id="+ID+"&appid="+API_KEY+"&units=metric";
        TextView url = findViewById(R.id.url);
        url.setText(URL);
        DownloadFilesTask mDownloadFilesTask = new DownloadFilesTask();
        mDownloadFilesTask.execute(URL);

    }

    public Date getDate(){
        Date result = calendar.getTime();
        return result;
    }

    public int getDay(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }



    public int getSeason(){
        //Winter Spring Summer Autumn
        int result = 0;

        return result;
    }

    // Using G-SON
    public String parse2(String data){
        return null;
    }

    // Using json.org
    public Location parse(String data){
        try {
            JSONObject obj = new JSONObject(data);
            // Coords
            Double lon = obj.getJSONObject("coord").getDouble("lon");
            Double lat = obj.getJSONObject("coord").getDouble("lat");

            // Weather
            JSONArray arr = obj.getJSONArray("weather");
            int wId = arr.getJSONObject(0).getInt("id");
            String main = arr.getJSONObject(0).getString("main");
            String desc = arr.getJSONObject(0).getString("description");
            String icon = arr.getJSONObject(0).getString("icon");

            // Main
            double temp = obj.getJSONObject("main").getDouble("temp");
            //int pressure = obj.getJSONObject("main").getInt("pressure");
            int humidity = obj.getJSONObject("main").getInt("humidity");
            int temp_min = obj.getJSONObject("main").getInt("temp_min");
            int temp_max = obj.getJSONObject("main").getInt("temp_max");

            // Visibility
            double visibility = obj.getDouble("visibility");
            double speed = obj.getJSONObject("wind").getDouble("speed");
            double deg = obj.getJSONObject("wind").getDouble("deg");
            int clouds = obj.getJSONObject("clouds").getInt("all");

            // Time
            int time = obj.getInt("dt");
            // Sys
            String country = obj.getJSONObject("sys").getString("country");
            int sunrise = obj.getJSONObject("sys").getInt("sunrise");
            int sunset = obj.getJSONObject("sys").getInt("sunset");

            // City
            int cityId = obj.getInt("id");
            String name = obj.getString("name");

            Location location = new Location(lon,lat,wId,main,desc,icon,temp,humidity,temp_min,temp_max,visibility,speed,deg,clouds,time,country,sunrise,sunset,name,cityId);
            return location;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    class DownloadFilesTask extends AsyncTask<String, Integer, Location> {

        @Override
        protected Location doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                // Connect to the server
                HttpURLConnection conn;
                conn = (HttpURLConnection)url.openConnection();
                int result = conn.getResponseCode();

                // Storing Data
                String line;
                StringBuilder text = new StringBuilder();

                if (result == HttpURLConnection.HTTP_OK) {
                    // Input stream for HTTP Connection
                    InputStream in = conn.getInputStream();
                    InputStreamReader inputReader = new InputStreamReader(in);
                    BufferedReader br = new BufferedReader(inputReader);
                    // Build the string for getting price
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                        System.out.println(line);
                    }

                    // Close the input streams
                    br.close();
                    inputReader.close();
                    in.close();
                }
                String data = text.toString();

                return parse(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Location result) {
            TextView data = findViewById(R.id.data);
            if(result!=null) {
                data.setText(result.toString());
            } else {
                data.setText("Error");
            }

        }
    }
}



