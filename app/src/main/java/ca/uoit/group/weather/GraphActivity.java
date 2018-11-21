package ca.uoit.group.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;



public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        LineChart chart = findViewById(R.id.chart);
        ForecastData forecast = (ForecastData)(getIntent().getSerializableExtra("data"));

        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < 38; i++) {
            // turn your data into Entry objects
            entries.add(new Entry((float)forecast.getWeatherData(i).getPreciseTemp(), (float)i*3));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Forecast for" + forecast.getWeatherData(0).getCityName()); // add entries to dataset

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

    public void close(View view){

    }

}
