package com.gmartinsribeiro.weather.android.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Gonçalo Martins Ribeiro on 08-07-2015.
 */
public class Locator implements LocationListener {

    static private final String TAG = "Locator";

    static private final int TIME_INTERVAL = 100; // minimum time between updates in milliseconds
    static private final int DISTANCE_INTERVAL = 1; // minimum distance between updates in meters

    static public enum Method {
        NETWORK,
        GPS,
        NETWORK_THEN_GPS
    }

    private Context mContext;
    private LocationManager mLocationManager;
    private Locator.Method mMethod;
    private Locator.Listener mCallback;

    public Locator(Context context) {
        super();
        this.mContext = context;
        this.mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void getLocation(Locator.Method method, Locator.Listener callback) {
        this.mMethod = method;
        this.mCallback = callback;
        switch (this.mMethod) {
            case NETWORK:
            case NETWORK_THEN_GPS:
                Location networkLocation = this.mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (networkLocation != null) {
                    Log.d(TAG, "Last known location found for network provider : " + networkLocation.toString());
                    this.mCallback.onLocationFound(networkLocation);
                } else {
                    Log.d(TAG, "Request updates from network provider.");
                    this.requestUpdates(LocationManager.NETWORK_PROVIDER);
                }
                break;
            case GPS:
                Location gpsLocation = this.mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (gpsLocation != null) {
                    Log.d(TAG, "Last known location found for GPS provider : " + gpsLocation.toString());
                    this.mCallback.onLocationFound(gpsLocation);
                } else {
                    Log.d(TAG, "Request updates from GPS provider.");
                    this.requestUpdates(LocationManager.GPS_PROVIDER);
                }
                break;
        }
    }

    private void requestUpdates(String provider) {
        if (this.mLocationManager.isProviderEnabled(provider)) {
            if (provider.contentEquals(LocationManager.NETWORK_PROVIDER)
                    && Connectivity.isConnected(this.mContext)) {
                Log.d(TAG, "Network connected, start listening : " + provider);
                this.mLocationManager.requestLocationUpdates(provider, TIME_INTERVAL, DISTANCE_INTERVAL, this);
            } else if (provider.contentEquals(LocationManager.GPS_PROVIDER)
                    && Connectivity.isConnectedMobile(this.mContext)) {
                Log.d(TAG, "Mobile network connected, start listening : " + provider);
                this.mLocationManager.requestLocationUpdates(provider, TIME_INTERVAL, DISTANCE_INTERVAL, this);
            } else {
                Log.d(TAG, "Proper network not connected for provider : " + provider);
                this.onProviderDisabled(provider);
            }
        } else {
            this.onProviderDisabled(provider);
        }
    }

    public void cancel() {
        Log.d(TAG, "Locating canceled.");
        this.mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location found : " + location.getLatitude() + ", " + location.getLongitude() + (location.hasAccuracy() ? " : +- " + location.getAccuracy() + " meters" : ""));
        this.mLocationManager.removeUpdates(this);
        this.mCallback.onLocationFound(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "Provider disabled : " + provider);
        if (this.mMethod == Locator.Method.NETWORK_THEN_GPS
                && provider.contentEquals(LocationManager.NETWORK_PROVIDER)) {
            // Network provider disabled, try GPS
            Log.d(TAG, "Request updates from GPS provider, network provider disabled.");
            this.requestUpdates(LocationManager.GPS_PROVIDER);
        } else {
            this.mLocationManager.removeUpdates(this);
            this.mCallback.onLocationNotFound();
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "Provider enabled : " + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "Provided status changed : " + provider + " : status : " + status);
    }

    public interface Listener {
        void onLocationFound(Location location);

        void onLocationNotFound();
    }

    public boolean hasGpsEnabled() {
        return this.mLocationManager.isProviderEnabled( LocationManager.GPS_PROVIDER );
    }

}