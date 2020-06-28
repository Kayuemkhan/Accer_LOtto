package com.morangesoft.lotteryapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.morangesoft.lotteryapp.Prevalent.Prevalent;

import java.util.HashMap;

public class CashMatchPercentage extends AppCompatActivity {
    private EditText ventas, comission, premios, resultado, deuda_anterior;
    private Button submit_btn;
    private DatabaseReference databaseReference;
    String user = Prevalent.currentOnlineUser.getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_match_percentage);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cash Match");


        ventas = findViewById(R.id.editTextPhone6);
        comission = findViewById(R.id.editTextPhone7);
        premios = findViewById(R.id.editTextPhone8);
        resultado = findViewById(R.id.editTextPhone9);
        deuda_anterior = findViewById(R.id.editTextPhone10);

        submit_btn = findViewById(R.id.button);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                loaddata();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loaddata() {
         String venta = ventas.getText().toString();
         String comiss = comission.getText().toString();
         String premio = premios.getText().toString();
         String resaltado = resultado.getText().toString();
         String deuda = deuda_anterior.getText().toString();
         if(TextUtils.isEmpty(venta)){
             ventas.setError("Please Give the information");
         }else if(TextUtils.isEmpty(comiss)){
             comission.setError("Please Give the information");
         }else if(TextUtils.isEmpty(premio)){
             premios.setError("Please Give the information");
         }else if(TextUtils.isEmpty(resaltado)){
             resultado.setError("Please Give the information");
         }else if(TextUtils.isEmpty(deuda)){
             deuda_anterior.setError("Please Give the information");
         }
         else {
             addDatatoFirebase(venta,comiss,premio,resaltado,deuda); }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addDatatoFirebase(String venta, String comiss, String premio, String resaltado, String deuda) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Commission",comiss);
        hashMap.put("Deuda",deuda);
        hashMap.put("Premios",premio);
        hashMap.put("Resultad",resaltado);
        hashMap.put("Ventas",venta);
        databaseReference.child(user).child(java.time.LocalDate.now().toString()).setValue(hashMap).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(CashMatchPercentage.this,MainActivity.class));
                        finish();
                    }
                }
        );

    }


}