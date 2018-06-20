package com.marko.travelers.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.marko.travelers.R;

public class TravelMyActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "TravelMyActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISIONS_REQUEST_CODE = 1234;

    private Boolean locationPermissionsGranted = false;
    private GoogleMap Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_my);

        getLocationPermissions();

        FillAcrivity();

    }

    private void FillAcrivity(){
        Intent intent = getIntent();
        String UserName = intent.getStringExtra("UserName");
        String From = intent.getStringExtra("From");
        String To = intent.getStringExtra("To");
        String Date = intent.getStringExtra("Date");
        String Time = intent.getStringExtra("Time");
        String Phone = intent.getStringExtra("Phone");
        int Image = intent.getIntExtra("Image", 0);

        ImageView imageView = findViewById(R.id.image_d);
        TextView User = findViewById(R.id.username_d);
        TextView from = findViewById(R.id.from_d);
        TextView to = findViewById(R.id.to_d);
        TextView date = findViewById(R.id.date_d);
        TextView time = findViewById(R.id.time_d );
        TextView phone = findViewById(R.id.phone_d );


        imageView.setImageResource(Image);
        User.setText(UserName);
        from.setText(From);
        to.setText(To);
        date.setText(Date);
        time.setText(Time);
        phone.setText(Phone);

    }


    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.my_map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Map = googleMap;
            }
        });
    }


    private void getLocationPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                locationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISIONS_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISIONS_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            locationPermissionsGranted = false;
                            return;
                        }
                        locationPermissionsGranted = true;
                        initMap();

                    }
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Map = googleMap;

    }

}
