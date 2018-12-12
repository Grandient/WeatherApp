package ca.uoit.group.weather;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Calendar;

import static java.lang.Math.abs;
import static java.lang.Math.log;

public class WeatherReceiver extends BroadcastReceiver {

    WeatherData weatherData;
    private int weatherTemp = 0;
    private double newTemperature = 0;



    @Override
    public void onReceive(Context context, Intent intent) {
        final String weatherChange = "Weather: ";
        String newTemp= "None";
        newTemperature = weatherData.getPreciseTemp();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

//
        long when = System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent notificationIntent = new Intent(context, MainMenuActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        if((when >= 60 * 1000) && (newTemperature == newTemperature + 1)){
            newTemperature++;

            //NOTIFICATION CODE
            String message = weatherChange + newTemp;

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1234")
                    .setSmallIcon(R.drawable.notification_icon)

                    .setContentTitle("*******Weather Owl*******")
                    .setContentText("")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            notificationManager.notify(weatherTemp, mBuilder.build());
            }


            if ((when >= 60 * 1000) && (newTemperature == newTemperature - 1)) {
                newTemperature--;

                //NOTIFICATION CODE
                newTemp = String.valueOf(newTemperature);

                String message = weatherChange + newTemp;

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1234")
                        .setSmallIcon(R.drawable.notification_icon)

                        .setContentTitle("*******Weather Owl*******")
                        .setContentText("")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                notificationManager.notify(weatherTemp, mBuilder.build());
            }

        }
   }


