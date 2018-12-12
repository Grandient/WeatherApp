package ca.uoit.group.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CityListAdapter extends ArrayAdapter<LocationData> {

    private Context context;
    private List<LocationData> data;
    private boolean showCheckBoxes;

    public CityListAdapter(Context context, List<LocationData> data) {
        super(context, 0, data);

        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Potentially reuse the list item view
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.city_list_item, parent, false);
        }

        // Collect data to be inserted
        LocationData city = data.get(position);

        // Populate the UI
        TextView cityName = listItem.findViewById(R.id.labelCityName);
        cityName.setText(city.getCityName());

        return listItem;
    }
}

