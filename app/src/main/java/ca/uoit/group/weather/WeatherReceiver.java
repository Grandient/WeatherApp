package ca.uoit.group.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Calendar;

import static java.lang.Math.abs;

public class WeatherReceiver extends BroadcastReceiver {

    WeatherData weatherData;
    private int weatherTemp = 0;

    int prevMin;
    int currentMin;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String weatherChange = "Weather: ";
        String newTemp = "None";

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        prevMin = currentMin;
        while (true) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int currentMin = calendar.get(Calendar.MINUTE);
            if (currentMin != prevMin) {

                if (weatherData.getPreciseTemp() == weatherData.getPreciseTemp() + abs(1)) {
                    newTemp = weatherData.getStringTemp();
                }

                if (weatherData.getPreciseTemp() == weatherData.getPreciseTemp() - abs(1)) {
                    newTemp = weatherData.getStringTemp();
                }
            }


            //NOTIFICATION CODE

            String message = weatherChange + newTemp;

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1234")
                    .setSmallIcon(R.drawable.notification_icon)

                    .setContentTitle("*******Weather Owl*******")
                    .setContentText("")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_MAX);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(weatherTemp, mBuilder.build());
        }
    }
}
