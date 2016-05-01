package com.example.sasab.bd_travellers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class PlaceSearch extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setOnClickListener();
    }

    private void setOnClickListener() {
        findViewById(R.id.button12).setOnClickListener(this);
        findViewById(R.id.button13).setOnClickListener(this);
        findViewById(R.id.button14).setOnClickListener(this);
        findViewById(R.id.button15).setOnClickListener(this);
        findViewById(R.id.button16).setOnClickListener(this);
        findViewById(R.id.button17).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String info = "Demo info";
        if(v.getId() == R.id.button12){
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(info)
                    .setTitle("Description:");

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if(v.getId() == R.id.button13){
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(info)
                    .setTitle("Description:");

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if(v.getId() == R.id.button14){
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(info)
                    .setTitle("Description:");

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        if(v.getId() == R.id.button15){
            Toast.makeText(this, "Prev", Toast.LENGTH_SHORT).show();
        }
        if(v.getId() == R.id.button16){
            Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
        }
        if(v.getId() == R.id.button17){
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }
}
