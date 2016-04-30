package com.example.sasab.bd_travellers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener  {

    private GoogleApiClient mGoogleApiClient;
    public String name;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile()
                .build();

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API).build();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        setOnClickListener();
    }

    private void setOnClickListener() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button){
            //Toast.makeText(this, "Hello Listeners" , Toast.LENGTH_SHORT).show();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            // [START_EXCLUDE]
                            Toast.makeText(HomeActivity.this, "Signing Out" , Toast.LENGTH_SHORT).show();
                            finish();
                            // [END_EXCLUDE]
                        }
                    });
        }
        if(v.getId() == R.id.button2){
            Intent intent = new Intent(this, PlaceSearch.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.button3){
            Intent intent = new Intent(this, GroupManagement.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.button4){
            Intent intent = new Intent(this, ManageTours.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.button5){
            View b = findViewById(R.id.button7);
            changeVisibility(b);
            View b2 = findViewById(R.id.button6);
            changeVisibility(b2);
        }
        if(v.getId() == R.id.button6){
            View b = findViewById(R.id.button7);
            changeVisibility(b);
            View b2 = findViewById(R.id.button6);
            changeVisibility(b2);
        }
        if(v.getId() == R.id.button7){
            View b = findViewById(R.id.button7);
            changeVisibility(b);
            View b2 = findViewById(R.id.button6);
            changeVisibility(b2);


            Toast.makeText(this, "Hello " + name +   ", Sending notification", Toast.LENGTH_SHORT).show();
            sendNotification();
        }
    }

    public void changeVisibility(View view){
        if(view.getVisibility() == View.VISIBLE) view.setVisibility(View.INVISIBLE);
        else if(view.getVisibility() == View.INVISIBLE) view.setVisibility(View.VISIBLE);
    }

    private void sendNotification() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed" , Toast.LENGTH_SHORT).show();
    }

}

