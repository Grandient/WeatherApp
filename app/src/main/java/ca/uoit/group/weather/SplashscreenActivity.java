package ca.uoit.group.weather;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.location.LocationManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class SplashscreenActivity extends AppCompatActivity {

    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET};
    private static final int REQUEST_PERMS_CODE = 1;

    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            // Play owl "hoot" sound on startup
            AssetFileDescriptor hootAudioFile = getAssets().openFd("owl.mp3");
            SoundPool soundPool = new SoundPool.Builder().build();
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    if (status == 0) {
                        // Load file success, play the sound
                        soundPool.play(soundId, 1.0f, 1.0f,
                                1, 0, 1);
                        //soundPool.release();
                    }
                }
            });
            soundId = soundPool.load(hootAudioFile, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if app has all permissions (for above Marshmallow, API Level 23)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : PERMISSIONS) {
                if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)) {
                    requestPermissions(new String[]{permission}, REQUEST_PERMS_CODE);
                }
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

                    startActivity(new Intent(SplashscreenActivity.this,
                            MainMenuActivity.class));
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        delayThread.start();
    }

}
