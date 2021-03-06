package com.morangesoft.lotteryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.morangesoft.lotteryapp.Prevalent.Prevalent;

public class Check2 extends AppCompatActivity {
    private TextView txtScanResult;
    private String scanResult;
    private DatabaseReference databaseReference, databaseReference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check2);
        txtScanResult = (TextView) findViewById(R.id.txtScanResult22);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lottery");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Lottery List");
        databaseReference.keepSynced(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            scanResult = extras.getString("ResultText"); /* Retrieving text of QR Code */
            txtScanResult.setText(scanResult);

        }
        dataChecktoFirebase();
    }
    private void dataChecktoFirebase() {
        databaseReference.orderByChild("lotterynum").equalTo(scanResult).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    databaseReference2.child((Prevalent.currentOnlineUser.getEmail())).child("lotterynum").setValue(scanResult);
                    startActivity(new Intent(Check2.this, MainActivity.class));
                    finish();
                } else {
                    final AlertDialog alertDialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(Check2.this);
                    alertDialog = builder.create();
                    final TextView title = new TextView(getApplicationContext());
                    title.setText("Copy");
                    title.setBackgroundColor(getColor(R.color.Black));
                    title.setPadding(10, 15, 15, 10);
                    title.setGravity(Gravity.CENTER);
                    title.setTextColor(Color.WHITE);
                    title.setTextSize(22);
                    alertDialog.setCustomTitle(title);
                    alertDialog.setMessage("No se encontró ninguna impresora");
                    alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(Check2.this, MainActivity.class));
                        }
                    });
                    alertDialog.show();
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


}
}