package com.example.villageplanner.HomeLogic;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.villageplanner.accountManager.AccountPage;
import com.example.villageplanner.BuildConfig;
import com.example.villageplanner.R;
import com.example.villageplanner.ReminderLogic.ReminderPage;
import com.example.villageplanner.Store;
import com.example.villageplanner.directionHelpers.FetchURL;
import com.example.villageplanner.directionHelpers.TaskLoadedCallback;
import com.example.villageplanner.directionHelpersTime.FetchURLTime;
import com.example.villageplanner.directionHelpersTime.TaskLoadedCallbackTime;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Stack;

public class HomepageActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback, TaskLoadedCallbackTime {

    private GoogleMap mMap;
    FusedLocationProviderClient mFusedLocationClient;
    boolean locationPermissionGranted;
    private Location lastKnownLocation;
    private static final int DEFAULT_ZOOM = 15;
    private final LatLng defaultLocation = new LatLng(34.025777343183165, -118.2849796291695);
    boolean locationAvailable;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private CameraPosition cameraPosition;
    private Stack<Marker> markers;
    Polyline currPolyline;
    private HashMap<String, Store> storeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_home_page);
        //TODO: Create Store Map

        // Construct a FusedLocationProviderClient.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        markers = new Stack<>();
        checkMyPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (locationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            getDeviceLocation();
        }
    }

    private void checkMyPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(HomepageActivity.this,"Permission Granted",Toast.LENGTH_SHORT).show();
                locationPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            locationAvailable = true;
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        locationAvailable = false;
                        System.out.println("here");
                    }
                });
            }
            else locationAvailable = false;
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    public void buttonClick(View view) {
        while(!markers.isEmpty()) {
            markers.pop().remove();
        }
        getDeviceLocation();
        Spinner storeSpinner = (Spinner) findViewById(R.id.store);
        String storeName = String.valueOf(storeSpinner.getSelectedItem());
        //TODO: Use Store map here instead
        Store store = new Store(storeName);

        MarkerOptions storeMarker= new MarkerOptions().position(new LatLng(store.getLatitude(), store.getLongitude())).title(storeName);
        MarkerOptions myMarker;
        if (locationAvailable) {
            myMarker = new MarkerOptions().position(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude())).title("My Location");
        }
        else {
            myMarker = new MarkerOptions().position(defaultLocation).title("My Location");

        }
        route(myMarker,storeMarker, store);
        markers.add(mMap.addMarker(storeMarker));
        markers.add(mMap.addMarker(myMarker));

    }

    public void route(MarkerOptions myMarker, MarkerOptions storeMarker, Store store) {
        String url = getUrl(myMarker.getPosition(), storeMarker.getPosition(), "walking");
        System.out.println(url);
        new FetchURL(HomepageActivity.this).execute(url, "walking");
        new FetchURLTime(HomepageActivity.this).execute(url, "walking");
        int qtime = (int) store.queueTime();
        String qstring = (qtime != 0) ? String.valueOf(qtime) + " mins" : "Closed";
        String tstring = "...";
        String eta = "Queue: " + qstring + " | " +
                "Travel: " + tstring;
        TextView routingDisplay = (TextView) findViewById (R.id.routingdisplay);
        routingDisplay.setText(eta);
    }

    public static String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + BuildConfig.MAPS_API_KEY;
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if(currPolyline != null) {
            currPolyline.setTag("Route");
            currPolyline.remove();
        }
        currPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        System.out.println(values.getClass());
    }

    public void onTaskDoneTime(Object... values) {
        TextView routingDisplay = (TextView) findViewById (R.id.routingdisplay);
        String dispText = (String) routingDisplay.getText();
        String tstring = (String) values[0];
        dispText = dispText.substring(0, dispText.length() - 3);
        dispText += tstring;
//        routingDisplay.setText((String) values[0]);
        routingDisplay.setText(dispText);
    }

    public void goToReminders(View view) {
        Intent i = new Intent(HomepageActivity.this, ReminderPage.class);
        i.putExtra("location", lastKnownLocation);
        startActivity(i);
    }

    public void goToAccountPage(View view) {
        Intent i = new Intent(HomepageActivity.this, AccountPage.class);
        startActivity(i);
    }

}