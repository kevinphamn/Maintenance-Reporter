package com.example.cse110.map;

import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "ZAe2fMrl1qDlJe4EExrtimZwPWJ5G9HvURz6jprR", "epI02Me6dny9AXtCt0bKY91onpphrkiIHJPTpx2r");


        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button toReport = (Button) findViewById(R.id.toReport);
        toReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MapsActivity.this, MainActivity2Activity.class);
                startActivity(myIntent);
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        mMap.clear();
        onMapReady(mMap);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng priceCenter = new LatLng(32.8799127, -117.2393386);
        //mMap.addMarker(new MarkerOptions().position(priceCenter).title("priceCenter"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((priceCenter), 14));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this, Main2Report.class);
                startActivity(intent);
            }
        });
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("DataPoint");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    List<String> rooms = new ArrayList<String>();
                    for (ParseObject entries : objects) {
                        ParseGeoPoint geolocation = entries.getParseGeoPoint("Location");
                        double latitude = geolocation.getLatitude();
                        double longitude = geolocation.getLongitude();
                        LatLng location = new LatLng(latitude, longitude);
                        String name = entries.getString("Building");
                        for (ParseObject checker : objects) {
                            if (checker.getString("Building").equals(name)) {
                                if(!rooms.contains(checker.getString("RoomNumber"))) {
                                    rooms.add(checker.getString("RoomNumber"));
                                }
                            }
                        }
                        mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(name)
                                .snippet("Rooms reported: " + rooms));
                        rooms.clear();
                    }
                }
                else {
                    Log.d("data", "Error: " + e.getMessage());
                }
            }
        });
    }
}
