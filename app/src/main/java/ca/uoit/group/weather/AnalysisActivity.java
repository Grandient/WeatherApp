package ca.uoit.group.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        ScatterChart scatterChart = findViewById(R.id.chart);
        // creating list of entry
        ArrayList<Entry> entries = new ArrayList();

        //Temp Lat
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));

        ScatterDataSet dataset = new ScatterDataSet(entries,"Average Temperature");
        dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        dataset.setScatterShapeSize(10);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        /*ArrayList<String> labels = new ArrayList<String>;();
                labels.add("Tokyo");
                labels.add("Toronto");
                labels.add("Moscow");
                labels.add("London");
                labels.add("Delhi");
                labels.add("");
        */
        ScatterData data = new ScatterData(dataset);
        scatterChart.setData(data);

        scatterChart.animateY(5000);


    }

    public void back(View view){
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }

}
