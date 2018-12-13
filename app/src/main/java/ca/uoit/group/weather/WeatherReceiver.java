package ca.uoit.group.weather;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class WeatherReceiver extends BroadcastReceiver {

    private static int NOTIF_ID;
    private static String notifChannelId;
    private int weatherTemp = 0;

    public WeatherReceiver(String notifChannelId) {
        super();

        this.NOTIF_ID = 1;
        this.notifChannelId = notifChannelId;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        setNotificationText("City", "0", "Unknown", context);
    }

    public void setNotificationText(String city, String temp, String condition, Context context) {

        String weather = String.format(context.getString(R.string.temp_celsius), temp) + ", " + condition;

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, notifChannelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(city)
                .setContentText(weather)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(weather))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(weatherTemp, mBuilder.build());
    }
}


