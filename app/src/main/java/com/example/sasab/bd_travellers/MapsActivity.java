package com.example.sasab.bd_travellers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
                            OnMapReadyCallback,
                            GoogleMap.OnMyLocationButtonClickListener,
                            GoogleMap.OnMyLocationChangeListener,
                            View.OnClickListener{

    private GoogleMap mMap;
    public LatLng currentPosition;
    private Marker mMarker ;
/*
    public void getLocation(){
        Location location = mMap.getMyLocation();
        currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        //Disable Map Toolbar:
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker
        currentPosition = new LatLng(23.745, 90.43);
        addMarker();

        findViewById(R.id.button).setOnClickListener(this);
    }

    private void addMarker()
    {
        mMarker= mMap.addMarker(new MarkerOptions().position(currentPosition).title(currentPosition.latitude + " " + currentPosition.longitude));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
    }

    @Override
    public boolean onMyLocationButtonClick() {

        Intent intent = null;
        try {
            Class ourClass = Class.forName("com.example.sasab.bd_travellers.SignInActivity");
            intent  = new Intent(MapsActivity.this, ourClass);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return false;
    }


    @Override
    public void onMyLocationChange(Location location) {
        currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mMarker.remove();
        addMarker();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            Toast.makeText(this, "Lat: " + currentPosition.latitude + " Lng: " + currentPosition.longitude , Toast.LENGTH_SHORT).show();
        }
    }
}
