package ca.uoit.group.weather;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ManageCitiesActivity extends AppCompatActivity {

    private LocationDBHelper locationDBHelper;
    private List<LocationData> locationList;
    //private static int numCitiesChecked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_cities);

        // Get location db
        locationDBHelper = new LocationDBHelper(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get locations
        locationList = locationDBHelper.getAllLocations();

        // Populate the ListView of cities
        final ListView cityList = findViewById(R.id.listCities);
        CityListAdapter contactAdapter = new CityListAdapter(this, locationList);
        cityList.setAdapter(contactAdapter);

        /*
        // Add click listeners to all list items' checkboxes
        for (int i = 0; i < cityList.getCount(); i++) {
            View listItem = cityList.getAdapter().getView(i, cityList.getChildAt(i), cityList);
            // Add click listener
            ((CheckBox)listItem.findViewById(R.id.checkBoxCity)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked");
                    if (((CheckBox)view).isChecked()) {
                        // Show delete button if at least one checkbox is checked
                        showDeleteButton(true);
                        numCitiesChecked++;
                    } else {
                        numCitiesChecked--;
                        if (numCitiesChecked == 0) {
                            showDeleteButton(false);
                        }
                    }
                }
            });
        }
        */

        // Delete button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.deleteCitiesFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View listItem;
                ArrayList<LocationData> citiesToDelete = new ArrayList<>();
                for (int i = 0; i < cityList.getCount(); i++) {
                    listItem = cityList.getAdapter().getView(i, cityList.getChildAt(i), cityList);

                    // Delete checked contacts from arraylist
                    if (((CheckBox)listItem.findViewById(R.id.checkBoxCity)).isChecked()) {
                        citiesToDelete.add((LocationData)(cityList.getAdapter().getItem(i)));
                    }
                }

                // Show pop-up confirmation to delete contacts if at least one contact is selected
                if (citiesToDelete.size() > 0) {
                    showPopupDeleteConfirmation(cityList, citiesToDelete);
                }
            }
        });
    }

    private void showPopupDeleteConfirmation(final ListView cityList, final ArrayList<LocationData> citiesToDelete) {
        AlertDialog.Builder alert = new AlertDialog.Builder(ManageCitiesActivity.this);
        alert.setTitle("Confirm Delete");

        int numCitiesToDelete = citiesToDelete.size();
        String deleteMessage = "Are you sure you want to delete the " + numCitiesToDelete + " selected cities?";
        if (numCitiesToDelete == 1) {
            deleteMessage = "Are you sure you want to delete the selected city?";
        }
        alert.setMessage(deleteMessage);
        alert.setNegativeButton("No", null);
        alert.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Remove contacts from arraylist
                while (citiesToDelete.size() > 0) {
                    LocationData locToDel = citiesToDelete.remove(0);
                    locationList.remove(locToDel);
                    locationDBHelper.deleteLocation(locToDel.getCityName());
                }

                // Refresh listview to remove deleted contacts
                cityList.invalidateViews();

                // Uncheck checkboxes of items at previous indexes
                View listItem;
                for (int i = 0; i < locationList.size(); i++) {
                    listItem = cityList.getAdapter().getView(i, cityList.getChildAt(i), cityList);
                    ((CheckBox)listItem.findViewById(R.id.checkBoxCity)).setChecked(false);
                }
            }
        });
        alert.show();
    }

    private void showDeleteButton(boolean show) {
        int visibility = (show) ? View.VISIBLE : View.INVISIBLE;
        findViewById(R.id.deleteCitiesFab).setVisibility(visibility);
    }
}
