package com.stackbuffers.myguardianangels.manager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.stackbuffers.myguardianangels.interfaces.CallbackListener;

public class LocationManager {

    private static LocationCallback mLocationCallback;

    public static void getCurrentLocation(Activity context, CallbackListener callbackListener) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(context);
                return;
            }
            fusedLocationClient.getLastLocation().addOnSuccessListener(context, location -> {
                if (location != null) {
                    callbackListener.onReceiveData(location);
                } else {
                    if (isGpsProviderEnabled(context)) {
                        findCurrentLocation(context,callbackListener);
                    } else
                        Toast.makeText(context, "Turn on device GPS location", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("TAG", "getCurrentLocation exception : " + e.getMessage());
        }
    }

    private static void sendLocationToServer(Activity activity, Location location) {
        if (activity != null && location != null) {
        }
    }

    private static void findCurrentLocation(Activity context, CallbackListener callbackListener) {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    removeLocationUpdates(context);
                    callbackListener.onReceiveData(location);

                }
            }
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private static void removeLocationUpdates(Activity context) {
        if (mLocationCallback != null)
            LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(mLocationCallback);
    }

    private static void requestPermissions(Activity context) {
        try {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 5);
        } catch (Exception e) {
            Log.d("TAG", "checkPermissions:Exception : " + e.getMessage());
        }
    }

    public static boolean isGpsProviderEnabled(Activity context) {
        android.location.LocationManager service = (android.location.LocationManager) context.getSystemService(android.content.Context.LOCATION_SERVICE);
        return service.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }
}
