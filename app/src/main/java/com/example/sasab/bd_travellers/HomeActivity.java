package com.example.sasab.bd_travellers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setOnClickListener();
    }

    private void setOnClickListener() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button){
            Toast.makeText(this, "Hello Listeners" , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent("com.example.sasab.bd_travellers.MAPSACTIVITY");
            startActivity(intent);
        }
        if(v.getId() == R.id.button2){
            Toast.makeText(this, "Hello Listeners" , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent("com.example.sasab.bd_travellers.SIGNINACTIVITY");
            startActivity(intent);
        }
    }
}
