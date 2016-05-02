package com.example.sasab.bd_travellers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationChangeListener,
        View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

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

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, AppController.hostIP + "BDTravellers/user_reviews.php",
                null , this , this);
        jsonRequest.setTag("Review Marker");
//         Adding request to request queue

        AppController.getInstance().addToRequestQueue(jsonRequest);


        //findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    private void addMarker()
    {
        mMarker= mMap.addMarker(new MarkerOptions().position(currentPosition).title(currentPosition.latitude + " " + currentPosition.longitude));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
    }

    @Override
    public boolean onMyLocationButtonClick() {
//        Intent intent = null;
//        try {
//            Class ourClass = Class.forName("android.intent.action.MAIN");
//            intent  = new Intent(MapsActivity.this, ourClass);
//        } catch (ClassNotFoundException e1) {
//            e1.printStackTrace();
//        }
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
        /*if(v.getId() == R.id.button){
            Toast.makeText(this, "Lat: " + currentPosition.latitude + " Lng: " + currentPosition.longitude , Toast.LENGTH_SHORT).show();
        }*/
        if(v.getId() == R.id.button2){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.review_dialog, null))
                    // Add action buttons
                    .setPositiveButton("Post Review", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                            postReviewToDatabase();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //LoginDialogFragment.this.getDialog().cancel();
                        }
                    });
            builder.create();
            builder.show();
        }
    }

    private void postReviewToDatabase() {

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            int success = response.getInt("success");
            //Toast.makeText(this,  "Success e error" , Toast.LENGTH_LONG).show();
            if (success == 1) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();

                JSONArray online_bus_list = response.getJSONArray("place_review");

                for (int i = 0; i < online_bus_list.length(); i++) {
                    JSONObject busAttributes = online_bus_list.getJSONObject(i);
                    String areaName = busAttributes.getString("areaName");
                    Integer rating = busAttributes.getInt("rating");
                    String review = busAttributes.getString("review");
                    Double lat = busAttributes.getDouble("lat");
                    Double lng = busAttributes.getDouble("lng");
                    LatLng position = new LatLng(lat, lng);
//                    Marker marker
                    //Toast.makeText(this,  "Lat " + lat + " Lan " + lng , Toast.LENGTH_LONG).show();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(areaName + ": Rating: " + rating).snippet(review));
                    Log.i("Database review", "Rating " + rating + " Review " + review + " Position " + position);
                }

            } else {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(this,  "Error e asche" , Toast.LENGTH_LONG).show();

        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null) {
            Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
        }

        if (error instanceof TimeoutError) {
            Log.e("Volley", "TimeoutError");
        }else if(error instanceof NoConnectionError){
            Log.e("Volley", "NoConnectionError");
        } else if (error instanceof AuthFailureError) {
            Log.e("Volley", "AuthFailureError");
        } else if (error instanceof ServerError) {
            Log.e("Volley", "ServerError");
        } else if (error instanceof NetworkError) {
            Log.e("Volley", "NetworkError");
        }
    }


}
