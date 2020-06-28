package com.morangesoft.lotteryapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.morangesoft.lotteryapp.Prevalent.Prevalent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CashMatchActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    String currentUserNumber, todaysDate;

    TextView TextViewcommission, TextViewdeuda, TextViewpremios, TextViewresultad, TextViewventas, TextViewbalance;

    private String Commission, Deuda, Premios, Resultad, Ventas, balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_match);

        Toolbar toolbar = findViewById(R.id.toolbar12);
        toolbar.setTitle("Cash Match");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextViewcommission = findViewById(R.id.commisionID);
        TextViewdeuda = findViewById(R.id.deudaID);
        TextViewpremios = findViewById(R.id.premiosID);
        TextViewresultad = findViewById(R.id.resultadoID);
        TextViewventas = findViewById(R.id.ventasID);
        TextViewbalance = findViewById(R.id.balanceID);

        currentUserNumber = Prevalent.currentOnlineUser.getEmail();

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        todaysDate = dateFormat.format(calendar.getTime());


        loadData();


    }


    private void loadData() {
        DatabaseReference reference = database.child("Cash Match").child(currentUserNumber).child(todaysDate);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Commission = dataSnapshot.child("Commission").getValue().toString().trim();
                Deuda = dataSnapshot.child("Deuda").getValue().toString().trim();
                Premios = dataSnapshot.child("Premios").getValue().toString().trim();
                Resultad = dataSnapshot.child("Resultad").getValue().toString().trim();
                Ventas = dataSnapshot.child("Ventas").getValue().toString().trim();

                balance = String.valueOf(Integer.valueOf(Commission)+Integer.valueOf(Deuda)+
                        Integer.valueOf(Premios)+Integer.valueOf(Resultad)+Integer.valueOf(Ventas));


                TextViewcommission.setText(Commission);
                TextViewdeuda.setText(Deuda);
                TextViewpremios.setText(Premios);
                TextViewresultad.setText(Resultad);
                TextViewventas.setText(Ventas);
                TextViewbalance.setText(balance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
