package ca.uoit.group.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class ManageCitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_cities);

        // Get location DB
        LocationDBHelper locationDBHelper = new LocationDBHelper(getApplicationContext());

        // Populate the ListView of cities
        ListView contactList = findViewById(R.id.listCities);
        CityListAdapter contactAdapter = new CityListAdapter(this, locationDBHelper.getAllLocations());
        contactList.setAdapter(contactAdapter);
    }
}
