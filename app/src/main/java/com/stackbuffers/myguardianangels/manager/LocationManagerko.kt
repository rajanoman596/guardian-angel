package com.stackbuffers.myguardianangels.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import com.stackbuffers.myguardianangels.interfaces.CallbackListener
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.stackbuffers.myguardianangels.manager.LocationManagerko
import com.google.android.gms.tasks.OnSuccessListener
import android.widget.Toast
import com.google.android.gms.location.*
import java.lang.Exception

object LocationManagerko {
    private var mLocationCallback: LocationCallback? = null
    fun getCurrentLocation(context: Activity, callbackListener: CallbackListener) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(context)
                return
            }
            fusedLocationClient.lastLocation.addOnSuccessListener(context) { location: Location? ->
                if (location != null) {
                    callbackListener.onReceiveData(location)
                } else {
                    if (isGpsProviderEnabled(context)) {
                        findCurrentLocation(context, callbackListener)
                    } else Toast.makeText(context, "Turn on device GPS location", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.d("TAG", "getCurrentLocation exception : " + e.message)
        }
    }

    private fun sendLocationToServer(activity: Activity?, location: Location?) {
        if (activity != null && location != null) {
        }
    }

    private fun findCurrentLocation(context: Activity, callbackListener: CallbackListener) {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }
                val location = locationResult.lastLocation
                if (location != null) {
                    removeLocationUpdates(context)
                    callbackListener.onReceiveData(location)
                }
            }
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    private fun removeLocationUpdates(context: Activity) {
        if (mLocationCallback != null) LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(mLocationCallback)
    }

    private fun requestPermissions(context: Activity) {
        try {
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 5)
        } catch (e: Exception) {
            Log.d("TAG", "checkPermissions:Exception : " + e.message)
        }
    }

    fun isGpsProviderEnabled(context: Activity): Boolean {
        val service = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return service.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}