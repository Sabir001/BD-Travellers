package com.example.sasab.bd_travellers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GiveReview extends AppCompatActivity implements View.OnClickListener,   Response.Listener<JSONObject>, Response.ErrorListener{

    String lat, lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lat = getIntent().getStringExtra("Lat");
        lng = getIntent().getStringExtra("Lng");

        Toast.makeText(this, lat + " ," + lng, Toast.LENGTH_SHORT).show();
        setOnClickListener();
    }

    private void setOnClickListener() {
        findViewById(R.id.button24).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String review, areaName;
        Integer rating;
        areaName =  ((EditText)findViewById(R.id.editText4)).getText().toString();
        review = ((EditText)findViewById(R.id.editText5)).getText().toString();
        rating =  Integer.parseInt(((EditText) findViewById(R.id.editText6)).getText().toString());

        if((rating > 5 && rating < 0)|| areaName.length() > 20 || review.length() > 100){
            Toast.makeText(this, "Constraints doesn't match", Toast.LENGTH_LONG).show();
            return;
        }

        Map<String , String> params = new HashMap<>();
        params.put("areaName", areaName);
        params.put("review", review);
        params.put("rating", rating.toString());
        params.put("lat", lat);
        params.put("lng", lng);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppController.hostIP + "BDTravellers/post_review.php",
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
        int success = 0;
        try {
            success = response.getInt("success");
            //Toast.makeText(this,  "Success e error" , Toast.LENGTH_LONG).show();
            if (success == 3) {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
