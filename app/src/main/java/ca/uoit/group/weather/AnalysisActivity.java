package ca.uoit.group.weather;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ArrayRes;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        scatter();
        radar();
    }

    public void radar(){
        RadarChart chart = findViewById(R.id.chart2);

        ArrayList<RadarEntry> entries = new ArrayList<>();
        entries.add(new RadarEntry(1, 0));
        entries.add(new RadarEntry(2, 1));
        entries.add(new RadarEntry(3, 2));
        entries.add(new RadarEntry(4, 3));
        entries.add(new RadarEntry(5, 4));

        ArrayList<RadarEntry> entries2 = new ArrayList<>();
        entries2.add(new RadarEntry(5, 0));
        entries2.add(new RadarEntry(4, 1));
        entries2.add(new RadarEntry(3, 2));
        entries2.add(new RadarEntry(2, 3));
        entries2.add(new RadarEntry(1, 4));

        RadarDataSet dataset_comp1 = new RadarDataSet(entries, "Toronto");
        RadarDataSet dataset_comp2 = new RadarDataSet(entries2, "Tokyo");

        dataset_comp1.setColor(Color.CYAN);
        dataset_comp2.setColor(Color.MAGENTA);

        dataset_comp1.setFillColor(Color.CYAN);
        dataset_comp2.setFillColor(Color.MAGENTA);

        dataset_comp1.setDrawFilled(true);
        dataset_comp2.setDrawFilled(false);

        final ArrayList<String> labels = new ArrayList<>();
        labels.add("Temperature");
        labels.add("Humidity");
        labels.add("Cloudiness");
        labels.add("Sunrise");
        labels.add("Sunset");


        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int) value);
            }});

        ArrayList<IRadarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset_comp1);
        dataSets.add(dataset_comp2);

        RadarData data = new RadarData(dataSets);
        data.setDrawValues(false);
        chart.setData(data);
        data.setValueTextColor(Color.WHITE);
        Description desc = new Description();
        desc.setText("Chart that shows how well the city is rated on a 1-5 scale.");
        chart.setDescription(desc);
        chart.setWebLineWidthInner(0.5f);
        chart.invalidate();
        chart.animate();

    }

    public void scatter(){
        ScatterChart scatterChart = findViewById(R.id.chart);
        // creating list of entry
        ArrayList<Entry> entries = new ArrayList<>();

        //Temp Lat
        entries.add(new Entry(0,20));
        entries.add(new Entry(1, 12));
        entries.add(new Entry(2, 30));
        entries.add(new Entry(3, 8));
        entries.add(new Entry(4, 2));
        entries.add(new Entry(5, 19));


        ScatterDataSet dataset = new ScatterDataSet(entries,"Cities");
        dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        dataset.setScatterShapeSize(10);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        final ArrayList<String> labels = new ArrayList<String>();
        labels.add("Tokyo");
        labels.add("Toronto");
        labels.add("Moscow");
        labels.add("London");
        labels.add("Delhi");
        labels.add("Los Angeles");
        XAxis xAxis = scatterChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int) value);
            }});




        ScatterData data = new ScatterData(dataset);
        scatterChart.setData(data);
        Description desc = new Description();
        desc.setText("Shows average temperature at different cities");
        scatterChart.setDescription(desc);
        scatterChart.animateY(2000);

    }

    public void back(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }

}
