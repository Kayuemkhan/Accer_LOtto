package com.morangesoft.lotteryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class Configartion extends AppCompatActivity {
    private LinearLayout linearLayout1,linearLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configartion);

        Toolbar toolbar = findViewById(R.id.toolbarConfigaration);
        toolbar.setTitle("Configuration");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayout1 = findViewById(R.id.pale_id);
        linearLayout2 = findViewById(R.id.seleccion_id);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paleOnclick();
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccion_click();
            }
        });

    }



    private void paleOnclick() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Configartion.this);
        View mView = getLayoutInflater().inflate(R.layout.pale_click_layout, null);
        builder.setView(mView);

        final TextView textView = mView.findViewById(R.id.pale_click_cancel);
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void seleccion_click() {
        final AlertDialog.Builder aleart = new AlertDialog.Builder(getApplicationContext());
        View mView = getLayoutInflater().inflate(R.layout.seleccion_de_loterias_click,null);
        aleart.setView(mView);

        final TextView textView = mView.findViewById(R.id.seleccion_click_cancel);

        final AlertDialog alertDialog = aleart.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}