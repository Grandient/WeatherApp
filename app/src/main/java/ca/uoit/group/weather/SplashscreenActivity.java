package ca.uoit.group.weather;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class SplashscreenActivity extends AppCompatActivity {

    // Permissions
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET};
    private static final int REQUEST_PERMS_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if app has all permissions
        for (String permission: PERMISSIONS) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{permission}, REQUEST_PERMS_CODE);
            }
        }

        // Check if user has GPS disabled
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Show popup to take user to settings to enable location services
            AlertDialog.Builder alert = new AlertDialog.Builder(SplashscreenActivity.this);
            alert.setTitle("Location Required");
            alert.setMessage("GPS and location access is needed. Go to Settings to enable it now?");
            alert.setNegativeButton("No", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String locationConfig = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
                    Intent enableLocationIntent = new Intent(locationConfig);
                    startActivity(enableLocationIntent);
                }
            });
            alert.show();
        }

        // Wait a bit
        Thread delayThread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    sleep(3000);

                    Intent mainAppIntent = new Intent(SplashscreenActivity.this,
                            MainMenuActivity.class);
                    startActivity(mainAppIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        delayThread.start();
    }

}
