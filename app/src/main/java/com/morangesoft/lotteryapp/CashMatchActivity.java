package com.morangesoft.lotteryapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    String Commission, Deuda, Premios, Resultad, Ventas, balance;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sorteos_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.vincular_impresora_sorteos){
            final AlertDialog.Builder aleart = new AlertDialog.Builder(CashMatchActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.error_layout,null);
            final Button button = mView.findViewById(R.id.btn_error);
            aleart.setView(mView);

            final AlertDialog alertDialog = aleart.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            if(item.getItemId() == R.id.whatsapp_sorteos){
                String number = "+51990148228" ;
                String url = "https://api.whatsapp.com/send?phone="+number;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        DatabaseReference reference = database.child("Cash Match").child(currentUserNumber).child(todaysDate);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Commission = dataSnapshot.child("Commission").getValue().toString().trim();
                    Deuda = dataSnapshot.child("Deuda").getValue().toString().trim();
                    Premios = dataSnapshot.child("Premios").getValue().toString().trim();
                    Resultad = dataSnapshot.child("Resultad").getValue().toString().trim();
                    Ventas = dataSnapshot.child("Ventas").getValue().toString().trim();

                    if (Commission.isEmpty()|| Deuda.isEmpty() || Premios.isEmpty() || Resultad.isEmpty() || Ventas.isEmpty()){

                        TextViewcommission.setText("0");
                        TextViewdeuda.setText("0");
                        TextViewpremios.setText("0");
                        TextViewresultad.setText("0");
                        TextViewventas.setText("0");
                        TextViewbalance.setText("0");

                    }else {

                        balance = String.valueOf(Integer.valueOf(Commission) + Integer.valueOf(Deuda) +
                                Integer.valueOf(Premios) + Integer.valueOf(Resultad) + Integer.valueOf(Ventas));


                        TextViewcommission.setText(Commission);
                        TextViewdeuda.setText(Deuda);
                        TextViewpremios.setText(Premios);
                        TextViewresultad.setText(Resultad);
                        TextViewventas.setText(Ventas);
                        TextViewbalance.setText(balance);
                }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
