package com.example.sasab.bd_travellers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManageTours extends AppCompatActivity implements View.OnClickListener , Response.Listener<JSONObject>, Response.ErrorListener {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_tours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView)findViewById(R.id.textView2);
        setTextViewMessage("No plans until now.\nAdd some ...");

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, "http://10.255.6.140/BDTravellers/group_plans.php",
                null , this , this);
        jsonRequest.setTag("Group Plans");
//         Adding request to request queue

        AppController.getInstance().addToRequestQueue(jsonRequest);

        setOnClickListener();
    }

    public void setTextViewMessage(String plans){
        textView.setText(plans);
    }

    private void setOnClickListener() {
        findViewById(R.id.button18).setOnClickListener(this);
        findViewById(R.id.button19).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button18){
            Intent intent = new Intent(this, ExpenditureManagement.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.button19){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.addplan_dialog, null))
                    // Add action buttons
                    .setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
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

    @Override
    public void onErrorResponse(VolleyError error) {
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

    @Override
    public void onResponse(JSONObject response) {
        String plans = "";
        try {
            int success = response.getInt("success");
            //Toast.makeText(this,  "Success e error" , Toast.LENGTH_LONG).show();
            if (success == 1) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();

                JSONArray planList = response.getJSONArray("place_review");

                for (int i = 0; i < planList.length(); i++) {
                    JSONObject busAttributes = planList.getJSONObject(i);
                    Integer rating = busAttributes.getInt("rating");
                    String review = busAttributes.getString("review");
                    Double lat = busAttributes.getDouble("lat");
                    Double lng = busAttributes.getDouble("lng");
                    LatLng position = new LatLng(lat, lng);
//                    Marker marker
                    //Toast.makeText(this,  "Lat " + lat + " Lan " + lng , Toast.LENGTH_LONG).show();
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Rating: " + rating).snippet(review));
                    Log.i("Database review", "Rating " + rating + " Review " + review + " Position " + position);
                }

            } else {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setTextViewMessage(plans);
    }

}
