package com.example.sasab.bd_travellers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ManageTours extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_tours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText("Plans\nHere Are Some Plans ...");

        setOnClickListener();
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
}
