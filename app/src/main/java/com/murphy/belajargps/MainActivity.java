package com.murphy.belajargps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnkalibrasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnkalibrasi = findViewById(R.id.kalibrasi);
        btnkalibrasi.setOnClickListener(arg0 -> {
            LocationManager mylocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener mylocationListener = new lokasiListener();
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mylocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 200, mylocationListener);
        });
    }

    private class lokasiListener implements LocationListener{
        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationChanged(@NonNull Location location) {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            
            TextView tvLat = findViewById(R.id.tvtLat);
            TextView tvLong = findViewById(R.id.tvtLong);
            TextView lokasi = findViewById(R.id.lokasi);

            try {
                //Log.e("latitude", "inside latitude--" + latitude);
                addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null && addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0);
//                    String city = addresses.get(0).getLocality();
//                    String state = addresses.get(0).getAdminArea();
//                    String country = addresses.get(0).getCountryName();
//                    String postalCode = addresses.get(0).getPostalCode();
//                    String knownName = addresses.get(0).getFeatureName();

                    lokasi.setText(String.format("%s", address));
                } else {
                    lokasi.setText("Latitude dan longitude tidak valid");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                lokasi.setText("Lokasi Tidak ditemukan");
            }

            tvLat.setText(String.valueOf(latitude));
            tvLong.setText(String.valueOf(longitude));
            Toast.makeText(getBaseContext(), "GPS Caputre", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }
}
