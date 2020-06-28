package com.morangesoft.lotteryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelectClass extends AppCompatActivity {
    private Button soteos, lotter,cashMatchPercentage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);
        Toolbar toolbar = findViewById(R.id.selectclasActivity);
        toolbar.setTitle("Selection");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cashMatchPercentage = findViewById(R.id.cash_mat);

        soteos = findViewById(R.id.sorteos_nym);
        lotter = findViewById(R.id.lottery_nym);
        soteos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectClass.this,SorteosInput.class));
                finish();
            }
        });
        lotter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectClass.this, AdminNActivity.class));
                finish();
            }
        });
        cashMatchPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectClass.this,CashMatchPercentage.class));
                finish();
            }
        });

    }
}