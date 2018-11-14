package ca.uoit.group.weather;

import android.Manifest;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main_Menu extends AppCompatActivity {

    private static final String FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String INTERNET = Manifest.permission.INTERNET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
    }

}

class DownloadFilesTask extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... locations) {
        try {
            // API URL to get data
            String API = "https://blockchain.info/tobtc?currency=CAD&value=";
            // Current location of user
            String location = locations[0];

            // URL for connecting with API
            URL url = new URL(API+location);

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
                }

                // Close the input streams
                br.close();
                inputReader.close();
                in.close();
            }

            // Parsing JSON

            return "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void onPostExecute(String result) {

    }

}

