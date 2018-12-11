package ca.uoit.group.weather;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.blue;
import static android.graphics.Color.rgb;


public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        LineChart chart = findViewById(R.id.chart);
        // Getting the data
        ForecastData forecast = (ForecastData)(getIntent().getSerializableExtra("data"));

        // Arraylists of Data
        ArrayList<Entry> temps = new ArrayList<>();
        ArrayList<Entry> minTemps = new ArrayList<>();
        ArrayList<Entry> maxTemps = new ArrayList<>();
        final String[] xLabel = new String[40];

        // Inputting data into arraylist
        for (int i = 0; i < 38; i++) {
            // turn your data into Entry objects
            // (x,y)
            temps.add(new Entry(i, (float) forecast.getWeatherData(i).getPreciseTemp()));
            minTemps.add(new Entry(i, (float) forecast.getWeatherData(i).getPreciseMinTemp()-10));
            maxTemps.add(new Entry(i, (float) forecast.getWeatherData(i).getPreciseMaxTemp()+10));
            xLabel[i] = forecast.getWeatherData(i).getTimeUpdated().substring(10,15);
        }

        // Changes Values to dates
        /*
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel[(int)value];
            }
        });*/

        ArrayList<ILineDataSet> lines = new ArrayList<>();

        // Expected (Purple)
        LineDataSet dataSet = new LineDataSet(temps, "Expected Temperature"); // add entries to dataset
        dataSet.setCircleColor(rgb(238,130,238));
        dataSet.setColor(rgb(238,130,238));
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(rgb(238,130,238));
        //dataSet.setFillDrawable(R.drawable.fade_red);
        lines.add(dataSet);

        // High (Red)
        LineDataSet dataSet2 = new LineDataSet(minTemps, "Expected Minimum");
        dataSet2.setCircleColor(Color.BLUE);
        dataSet2.setColor(Color.BLUE);
        dataSet2.setDrawFilled(true);
        dataSet2.setFillColor(BLUE);
        lines.add(dataSet2);


        // Low (Blue)
        LineDataSet dataSet3 = new LineDataSet(maxTemps, "Expeceted Maximum");
        dataSet3.setCircleColor(Color.RED);
        dataSet3.setColor(Color.RED);
        dataSet3.setDrawFilled(true);
        dataSet3.setFillColor(Color.RED);
        lines.add(dataSet3);

        LineData lineData = new LineData(lines);

        // Chart
        chart.setData(lineData);
        Description desc = new Description();
        desc.setText("This is the forecasting for the /city/." +
                "Weather forecast for 5 days with data every 3 hours by");
        chart.setDescription(desc);
        chart.setTouchEnabled(false);
        chart.invalidate(); // refresh
        chart.animateXY(2000, 2000);

    }



    public void close(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }

}
