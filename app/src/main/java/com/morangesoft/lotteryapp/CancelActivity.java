package com.morangesoft.lotteryapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.morangesoft.lotteryapp.Prevalent.Prevalent;

public class CancelActivity extends AppCompatActivity {
    private DatabaseReference databaseReference2;
    private EditText editText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        Toolbar toolbar = findViewById(R.id.cancelToolbar);
        toolbar.setTitle("Cancel Tikcets");
        setSupportActionBar(toolbar);

        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Lottery List");
        databaseReference2.keepSynced(true);
        editText =findViewById(R.id.cacelET);
        button = findViewById(R.id.cancelButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = editText.getText().toString();

                if(text.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter a Number",Toast.LENGTH_LONG).show();
                }

                else {
                    databaseReference2.orderByChild("lotterynum").equalTo(text).addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                dataSnapshot.getRef().setValue(null);
                                editText.setText("");
                                startActivity(new Intent(CancelActivity.this,MainActivity.class));
                            }
                            else {
                                final AlertDialog alertDialog;
                                AlertDialog.Builder builder = new AlertDialog.Builder(CancelActivity.this);
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
        });
    }




}
