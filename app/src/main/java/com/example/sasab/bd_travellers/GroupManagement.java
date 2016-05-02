package com.example.sasab.bd_travellers;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GroupManagement extends AppCompatActivity implements View.OnClickListener ,  Response.Listener<JSONObject>, Response.ErrorListener{

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //TextView textView = (TextView)findViewById(R.id.textView);
        //textView.setText("Group Members:\n");

        Map<String , String> params = new HashMap<>();
        params.put("email", HomeActivity.email);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppController.hostIP + "BDTravellers/group_members.php",
                jsonObject , this , this);
        AppController.getInstance().addToRequestQueue(jsonRequest);

        setOnClickListener();
    }

    private void setOnClickListener() {
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.button10).setOnClickListener(this);
        findViewById(R.id.button11).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button8){
            Toast.makeText(this, "Group Created", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.button9){

        }
        if (v.getId() == R.id.button10){
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Hello")
                    .setTitle("Testing");

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (v.getId() == R.id.button11){
            Toast.makeText(this, "Button 11", Toast.LENGTH_SHORT).show();
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
        Log.i("Manage tour request" , "Came here for response");
        String plans = "";
        try {
            int success = response.getInt("success");
            String ViewPlans[];
            //Toast.makeText(this,  "Success e error" , Toast.LENGTH_LONG).show();
            if (success == 1) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();

                JSONArray online_bus_list = response.getJSONArray("group_members");
                ViewPlans = new String[online_bus_list.length()];
                for (int i = 0; i < online_bus_list.length(); i++) {
                    JSONObject busAttributes = online_bus_list.getJSONObject(i);
                    plans += busAttributes.getString("Name");
                    ViewPlans[i]= busAttributes.getString("Name");
//                    Marker marker
                    //Toast.makeText(this,  "Lat " + lat + " Lan " + lng , Toast.LENGTH_LONG).show();
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(areaName + ": Rating: " + rating).snippet(review));
                    Log.i("Group plans: " , plans);
                }
                ListView list = (ListView) findViewById(R.id.list);
                assert list != null;
                list.setAdapter(new ArrayAdapter<>(GroupManagement.this, android.R.layout.simple_list_item_1, ViewPlans));

            } else {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
