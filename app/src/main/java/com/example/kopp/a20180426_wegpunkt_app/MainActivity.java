package com.example.kopp.a20180426_wegpunkt_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnWPSave;
    private Button btnWPShow;
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    private WegepunktRepo wegepunktRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isGPSEnabled = false;

        btnWPSave = findViewById(R.id.btnWPSave);
        btnWPSave.setEnabled(false);
        btnWPShow = findViewById(R.id.btnWPShow);

        wegepunktRepo = new WegepunktRepo();


        btnWPSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Meins", "SaveButton");
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("Meins", "Check fehlgeschlagen");
                    return;
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    WegePunkt wegePunkt = new WegePunkt(new Date(), location.getLatitude(), location.getLongitude());
                    wegepunktRepo.add(wegePunkt);
                    Log.d("Meins", wegePunkt.toString());
                }

            }
        });

        btnWPShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Meins", wegepunktRepo.toString());

                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                intent.putExtra("wegepunkte", wegepunktRepo);
                startActivity(intent);
            }
        });


    }

    private void initLocationManager() {
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Ich brauch diese Berechtigung ganz dringend!", Toast.LENGTH_SHORT).show();
            }

            requestPermission();
        } else {
            activateSaveLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 4711 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            activateSaveLocation();
        }
    }

    private void requestPermission() {
        String[] permissons = new String[1];
        permissons[0] = Manifest.permission.ACCESS_FINE_LOCATION;
        requestPermissions(permissons, 4711);
    }

    private void activateSaveLocation() {
        this.initLocationManager();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        btnWPSave.setEnabled(this.isGPSEnabled);
    }
}
