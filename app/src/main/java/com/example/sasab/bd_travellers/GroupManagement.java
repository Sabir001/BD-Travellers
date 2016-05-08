package com.example.sasab.bd_travellers;

import android.app.DialogFragment;
import android.content.Intent;
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

public class GroupManagement extends AppCompatActivity implements View.OnClickListener ,  Response.Listener<JSONObject>,
        Response.ErrorListener, AddUSerDialog.AddUserDialogListener {

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

        showMembers();

        setOnClickListener();
    }

    private void showMembers() {
        Map<String , String> params = new HashMap<>();
        params.put("email", HomeActivity.email);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppController.hostIP + "BDTravellers/group_members.php",
                jsonObject , this , this);
        AppController.getInstance().addToRequestQueue(jsonRequest);
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
            leaveGroup();
            createGroup();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        if (v.getId() == R.id.button9){
            //DialogFragment dialog = new AddUSerDialog();
            //dialog.show(getSupportFragmentManager(), "NotificationDialogFragment");
        }
        if (v.getId() == R.id.button10){
            Map<String , String> params = new HashMap<>();
            params.put("email", HomeActivity.email);

            JSONObject jsonObject = new JSONObject(params);

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppController.hostIP + "BDTravellers/group_code.php",
                    jsonObject , this , this);
            AppController.getInstance().addToRequestQueue(jsonRequest);


        }
        if (v.getId() == R.id.button11){
            leaveGroup();
            finish();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        AddUSerDialog addUSerDialog = (AddUSerDialog)dialog;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private void createGroup() {
        Map<String , String> params = new HashMap<>();
        params.put("email", HomeActivity.email);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppController.hostIP + "BDTravellers/createGroup.php",
                jsonObject , this , this);
        AppController.getInstance().addToRequestQueue(jsonRequest);

    }

    private void leaveGroup() {
        Map<String , String> params = new HashMap<>();
        params.put("email", HomeActivity.email);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppController.hostIP + "BDTravellers/leaveGroup.php",
                jsonObject , this , this);
        AppController.getInstance().addToRequestQueue(jsonRequest);
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
        //String secretCode = "";
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

            } else if (success == 0) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            } else if (success == 3) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();


                String secretCode = response.getString("code");


                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(secretCode)
                        .setTitle("Code:");

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            } else if(success == 2){
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            } else if(success == 5){
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                //showMembers();
            }else if(success == 4){
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            }  else if(success == 7){
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                //showMembers();
            }else if(success == 6){
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
