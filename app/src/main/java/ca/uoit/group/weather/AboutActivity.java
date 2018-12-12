package ca.uoit.group.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView tv = findViewById(R.id.textView);
        tv.setText("Access current weather data for any location on Earth including over 200,000 cities! " +
                "Current weather is frequently updated based on global models and data from more than 40,000 weather stations. " +
                "Data is available in JSON, XML, or HTML format.");
        tv.setMovementMethod(new ScrollingMovementMethod());

    }
}
