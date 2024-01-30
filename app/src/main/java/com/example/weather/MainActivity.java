package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    final String AppID = "8bbe32e8256923664fd5ade1d6c961c7";
    final String weatherURL = "https://api.openweathermap.org/data/2.5/weather";

    final long minTime = 5000;
    final float minDistance = 1000;
    final int requestCode = 101;

    String locationProvider = LocationManager.GPS_PROVIDER;
    TextView NameofCity, weatherState, Temperature;
    ImageView weatherIcon;
    RelativeLayout cityFinder;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherState = findViewById(R.id.weatherCondition);
        Temperature = findViewById(R.id.temperature);
        weatherIcon = findViewById(R.id.weatherIcon);
        cityFinder = findViewById(R.id.cityFinder);
        NameofCity = findViewById(R.id.CityName);

        cityFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, cityFinder.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getWeatherForCurrentLocation();
//    }

    @Override
    protected void onResume() {
        super.onResume();
     Intent intent = getIntent();
     String city = intent.getStringExtra("city");
     if (city != null) {
         getWeatherForCity(city);
     } else {
         getWeatherForCurrentLocation();
     }
    }

    private void getWeatherForCity(String city) {
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", AppID);
        letsDoSomeNetworking(params);
    }


    @SuppressLint("SuspiciousIndentation")
    private void getWeatherForCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                RequestParams params = new RequestParams();
                params.put("lat", latitude);
                params.put("lon", longitude);
                params.put("appid", AppID);
                letsDoSomeNetworking(params);

                // Now you have the latitude and longitude, and you can proceed with your weather API request.
                // Make sure to call the API and update the UI with the received weather information.
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Handle status changes if needed.
            }

            @Override
            public void onProviderEnabled(String provider) {
                // Handle provider enable if needed.
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Handle provider disable if needed.
            }
        };

        // Check for location permission here and request if necessary.

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)

        // Request location updates using the correct method.
        // Note: You should check the location permission before requesting updates.
        try {
            locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private class ACCESS_FINE_LOCATION {
    }

    private void letsDoSomeNetworking(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(weatherURL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                Toast.makeText(MainActivity.this, "Data  Received", Toast.LENGTH_SHORT).show();

                weatherData weatherD = weatherData.fromJson(response);
                updateUI(weatherD);
//                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable e, JSONObject response) {
                super.onFailure(statusCode, headers, e, response);
            }
        });
    }

    private void updateUI(weatherData weatherD) {
        Temperature.setText(weatherD.getTemperature());
        NameofCity.setText(weatherD.getNameofCity());
        weatherState.setText(weatherD.getWeatherState());
        int resourceID = getResources().getIdentifier(weatherD.getIcon(), "drawable", getPackageName());
        weatherIcon.setImageResource(resourceID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
