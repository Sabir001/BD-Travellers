package com.example.sasab.bd_travellers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowExpenditure extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenditure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Map<String , String> params = new HashMap<>();
        params.put("email", HomeActivity.email);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppController.hostIP + "BDTravellers/expenditure_group.php",
                jsonObject , this , this);
        AppController.getInstance().addToRequestQueue(jsonRequest);


        Map<String , String> params2 = new HashMap<>();
        params.put("email", HomeActivity.email);

        JSONObject jsonObject2 = new JSONObject(params);

        JsonObjectRequest jsonRequest2 = new JsonObjectRequest(Request.Method.POST, AppController.hostIP + "BDTravellers/expenditure_personal.php",
                jsonObject , this , this);
        AppController.getInstance().addToRequestQueue(jsonRequest2);

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
        Double priceGroup = 0.0;
        Double pricePersonal = 0.0;
        try {
            int success = response.getInt("success");
            String ViewPlans[];
            String ViewPlans2[];
            //Toast.makeText(this,  "Success e error" , Toast.LENGTH_LONG).show();
            if (success == 1) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();

                JSONArray planList = response.getJSONArray("expenditure");
                ViewPlans = new String[planList.length() + 1];
                for (int i = 0; i < planList.length(); i++) {
                    JSONObject busAttributes = planList.getJSONObject(i);
                    priceGroup += busAttributes.getDouble("price");
                    ViewPlans[i]=  busAttributes.getString("item") + ": "  + busAttributes.getString("price");
//                    Marker marker
                    //Toast.makeText(this,  "Lat " + lat + " Lan " + lng , Toast.LENGTH_LONG).show();
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(areaName + ": Rating: " + rating).snippet(review));
                    Log.i("Group plans: " , priceGroup.toString());
                }
                ViewPlans[planList.length()] = "Total Group Expenditure: " + priceGroup.toString();
                ListView list = (ListView) findViewById(R.id.list5);
                assert list != null;
                list.setAdapter(new ArrayAdapter<>(ShowExpenditure.this, android.R.layout.simple_list_item_1, ViewPlans));

            } else if(success == 0) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            } else if (success == 3) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();

                JSONArray planList = response.getJSONArray("expenditure");
                ViewPlans = new String[planList.length() + 1];
                for (int i = 0; i < planList.length(); i++) {
                    JSONObject busAttributes = planList.getJSONObject(i);
                    priceGroup += busAttributes.getDouble("price");
                    ViewPlans[i]=  busAttributes.getString("item") + ": "  + busAttributes.getString("price");
//                    Marker marker
                    //Toast.makeText(this,  "Lat " + lat + " Lan " + lng , Toast.LENGTH_LONG).show();
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(areaName + ": Rating: " + rating).snippet(review));
                    Log.i("Group plans: " , priceGroup.toString());
                }
                ViewPlans[planList.length()] = "Total Personal Expenditure: " + priceGroup.toString();
                ListView list = (ListView) findViewById(R.id.list6);
                assert list != null;
                list.setAdapter(new ArrayAdapter<>(ShowExpenditure.this, android.R.layout.simple_list_item_1, ViewPlans));

            } else if(success == 2) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
