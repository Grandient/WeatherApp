package ca.uoit.group.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WeatherReceiver extends BroadcastReceiver {
    WeatherData weatherData;
    @Override
    public void onReceive(Context context, Intent intent) {
        final String weatherChange = "Weather: ";

    }
}
