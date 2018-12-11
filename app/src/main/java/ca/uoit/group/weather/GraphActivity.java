package ca.uoit.group.weather;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
import static android.graphics.Color.rgb;


public class GraphActivity extends AppCompatActivity {

    ArrayList<Entry> temps = new ArrayList<>();
    ArrayList<Entry> minTemps = new ArrayList<>();
    ArrayList<Entry> maxTemps = new ArrayList<>();
    boolean d5 = true;
    boolean d3 = false;
    boolean d1 = false;
    boolean all = true;
    boolean temp = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Getting the data
        LineChart chart = findViewById(R.id.chart);
        ForecastData forecast = (ForecastData)(getIntent().getSerializableExtra("data"));
        // Arraylists of Data

        final String[] xLabel = new String[40];

        // Inputting data into arraylist
        for (int i = 0; i < 38; i++) {
            // turn your data into Entry objects
            // (x,y)
            temps.add(new Entry(i, (float) forecast.getWeatherData(i).getPreciseTemp()));
            minTemps.add(new Entry(i, (float) forecast.getWeatherData(i).getPreciseMinTemp()-ThreadLocalRandom.current().nextInt(4, 6 + 1)));
            maxTemps.add(new Entry(i, (float) forecast.getWeatherData(i).getPreciseMaxTemp()+ThreadLocalRandom.current().nextInt(4, 7 + 1)));
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
        LineDataSet dataSet3 = new LineDataSet(maxTemps, "Expected Maximum");
        dataSet3.setCircleColor(Color.RED);
        dataSet3.setColor(Color.RED);
        dataSet3.setDrawFilled(true);
        dataSet3.setFillColor(Color.RED);
        lines.add(dataSet3);

        LineData lineData = new LineData(lines);

        // Chart
        chart.setData(lineData);
        Description desc = new Description();
        desc.setText("This is the forecasting for the" + forecast.getWeatherData(1) +
                "Weather forecast for 5 days with data every 3 hours by");
        chart.setDescription(desc);
        chart.setTouchEnabled(false);
        chart.invalidate(); // refresh
        averages(forecast);
        chart.animateXY(2000, 2000);

    }

    public void show_5_days(View view){
        if(all) {
            LineChart chart = findViewById(R.id.chart);
            ArrayList<ILineDataSet> lines = new ArrayList<>();

            // Expected (Purple)
            LineDataSet dataSet = new LineDataSet(temps.subList(0, 8), "Expected Temperature"); // add entries to dataset
            dataSet.setCircleColor(rgb(238, 130, 238));
            dataSet.setColor(rgb(238, 130, 238));
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(rgb(238, 130, 238));
            //dataSet.setFillDrawable(R.drawable.fade_red);
            lines.add(dataSet);

            // High (Red)
            LineDataSet dataSet2 = new LineDataSet(minTemps.subList(0, 8), "Expected Minimum");
            dataSet2.setCircleColor(Color.BLUE);
            dataSet2.setColor(Color.BLUE);
            dataSet2.setDrawFilled(true);
            dataSet2.setFillColor(BLUE);
            lines.add(dataSet2);


            // Low (Blue)
            LineDataSet dataSet3 = new LineDataSet(maxTemps.subList(0, 8), "Expected Maximum");
            dataSet3.setCircleColor(Color.RED);
            dataSet3.setColor(Color.RED);
            dataSet3.setDrawFilled(true);
            dataSet3.setFillColor(Color.RED);
            lines.add(dataSet3);

            LineData lineData = new LineData(lines);

            // Chart
            chart.setData(lineData);
            Description desc = new Description();
            desc.setText("This is the forecasting for the" + "Weather forecast for 5 days with data every 3 hours by");
            chart.setDescription(desc);
            chart.setTouchEnabled(false);
            chart.invalidate(); // refresh
            chart.animateXY(2000, 2000);
        } else {
            LineChart chart = findViewById(R.id.chart);
            ArrayList<ILineDataSet> lines = new ArrayList<>();

            // Expected (Purple)
            LineDataSet dataSet = new LineDataSet(temps.subList(0, 8), "Expected Temperature"); // add entries to dataset
            dataSet.setCircleColor(rgb(238, 130, 238));
            dataSet.setColor(rgb(238, 130, 238));
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(rgb(238, 130, 238));
            //dataSet.setFillDrawable(R.drawable.fade_red);
            lines.add(dataSet);
            LineData lineData = new LineData(lines);

            // Chart
            chart.setData(lineData);
            Description desc = new Description();
            desc.setText("This is the forecasting for the Weather forecast for 5 days with data every 3 hours by");
            chart.setDescription(desc);
            chart.setTouchEnabled(false);
            chart.invalidate(); // refresh
            chart.animateXY(2000, 2000);
        }
    }

    public void show_3_days(View view){
        if(all) {
            LineChart chart = findViewById(R.id.chart);
            ArrayList<ILineDataSet> lines = new ArrayList<>();

            // Expected (Purple)
            LineDataSet dataSet = new LineDataSet(temps.subList(0, 24), "Expected Temperature"); // add entries to dataset
            dataSet.setCircleColor(rgb(238, 130, 238));
            dataSet.setColor(rgb(238, 130, 238));
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(rgb(238, 130, 238));
            //dataSet.setFillDrawable(R.drawable.fade_red);
            lines.add(dataSet);

            // High (Red)
            LineDataSet dataSet2 = new LineDataSet(minTemps.subList(0, 24), "Expected Minimum");
            dataSet2.setCircleColor(Color.BLUE);
            dataSet2.setColor(Color.BLUE);
            dataSet2.setDrawFilled(true);
            dataSet2.setFillColor(BLUE);
            lines.add(dataSet2);


            // Low (Blue)
            LineDataSet dataSet3 = new LineDataSet(maxTemps.subList(0, 24), "Expected Maximum");
            dataSet3.setCircleColor(Color.RED);
            dataSet3.setColor(Color.RED);
            dataSet3.setDrawFilled(true);
            dataSet3.setFillColor(Color.RED);
            lines.add(dataSet3);

            LineData lineData = new LineData(lines);

            // Chart
            chart.setData(lineData);
            Description desc = new Description();
            desc.setText("This is the forecasting for the" + "Weather forecast for 5 days with data every 3 hours by");
            chart.setDescription(desc);
            chart.setTouchEnabled(false);
            chart.invalidate(); // refresh
            chart.animateXY(2000, 2000);
        } else {
            LineChart chart = findViewById(R.id.chart);
            ArrayList<ILineDataSet> lines = new ArrayList<>();

            // Expected (Purple)
            LineDataSet dataSet = new LineDataSet(temps.subList(0, 24), "Expected Temperature"); // add entries to dataset
            dataSet.setCircleColor(rgb(238, 130, 238));
            dataSet.setColor(rgb(238, 130, 238));
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(rgb(238, 130, 238));
            //dataSet.setFillDrawable(R.drawable.fade_red);
            lines.add(dataSet);
            LineData lineData = new LineData(lines);

            // Chart
            chart.setData(lineData);
            Description desc = new Description();
            desc.setText("This is the forecasting for the" + "Weather forecast for 5 days with data every 3 hours by");
            chart.setDescription(desc);
            chart.setTouchEnabled(false);
            chart.invalidate(); // refresh
            chart.animateXY(2000, 2000);
        }
    }

    public void show_1_day(View view){
        if(all) {
            LineChart chart = findViewById(R.id.chart);
            ArrayList<ILineDataSet> lines = new ArrayList<>();

            // Expected (Purple)
            LineDataSet dataSet = new LineDataSet(temps.subList(0, 8), "Expected Temperature"); // add entries to dataset
            dataSet.setCircleColor(rgb(238, 130, 238));
            dataSet.setColor(rgb(238, 130, 238));
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(rgb(238, 130, 238));
            //dataSet.setFillDrawable(R.drawable.fade_red);
            lines.add(dataSet);

            // High (Red)
            LineDataSet dataSet2 = new LineDataSet(minTemps.subList(0, 8), "Expected Minimum");
            dataSet2.setCircleColor(Color.BLUE);
            dataSet2.setColor(Color.BLUE);
            dataSet2.setDrawFilled(true);
            dataSet2.setFillColor(BLUE);
            lines.add(dataSet2);


            // Low (Blue)
            LineDataSet dataSet3 = new LineDataSet(maxTemps.subList(0, 8), "Expected Maximum");
            dataSet3.setCircleColor(Color.RED);
            dataSet3.setColor(Color.RED);
            dataSet3.setDrawFilled(true);
            dataSet3.setFillColor(Color.RED);
            lines.add(dataSet3);

            LineData lineData = new LineData(lines);

            // Chart
            chart.setData(lineData);
            Description desc = new Description();
            desc.setText("This is the forecasting for the" + "Weather forecast for 5 days with data every 3 hours by");
            chart.setDescription(desc);
            chart.setTouchEnabled(false);
            chart.invalidate(); // refresh
            chart.animateXY(2000, 2000);

        } else {
            LineChart chart = findViewById(R.id.chart);
            ArrayList<ILineDataSet> lines = new ArrayList<>();

            // Expected (Purple)
            LineDataSet dataSet = new LineDataSet(temps.subList(0, 8), "Expected Temperature"); // add entries to dataset
            dataSet.setCircleColor(rgb(238, 130, 238));
            dataSet.setColor(rgb(238, 130, 238));
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(rgb(238, 130, 238));
            //dataSet.setFillDrawable(R.drawable.fade_red);
            lines.add(dataSet);

            LineData lineData = new LineData(lines);

            // Chart
            chart.setData(lineData);
            Description desc = new Description();
            desc.setText("This is the forecasting for the" + "Weather forecast for 5 days with data every 3 hours by");
            chart.setDescription(desc);
            chart.setTouchEnabled(false);
            chart.invalidate(); // refresh
            chart.animateXY(2000, 2000);
        }
    }

    public void showAll(View view){
        all = true;
        temp = false;
        Button b6 = findViewById(R.id.button6);
        Button b7 = findViewById(R.id.button7);
        b6.setClickable(false);
        b7.setClickable(true);
    }

    public void showTemp(View view){
        all = false;
        temp = true;
        Button b6 = findViewById(R.id.button6);
        Button b7 = findViewById(R.id.button7);
        b7.setClickable(false);
        b6.setClickable(true);
    }

    public void averages(ForecastData forecast){
        double alltemps = 0;
        for (int i = 0; i < 38; i++) {
            alltemps = forecast.getWeatherData(i).getPreciseTemp();
        }
        System.out.println(temps.get(0));

        double result = alltemps/38;
    }

    public void close(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }



}
