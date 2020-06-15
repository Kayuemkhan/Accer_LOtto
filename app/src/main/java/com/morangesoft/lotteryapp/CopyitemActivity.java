package com.morangesoft.lotteryapp;

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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.morangesoft.lotteryapp.Prevalent.Prevalent;

public class CopyitemActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private DatabaseReference databaseReference,databaseReference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copyitem);
        Toolbar toolbar = findViewById(R.id.copyToolbar);
        toolbar.setTitle("Copy Tickets");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lottery");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Lottery List");
        databaseReference.keepSynced(true);

        editText =findViewById(R.id.copyET);
        button = findViewById(R.id.copyaddButton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              final String text = editText.getText().toString();

              if(text.isEmpty()){
                  Toast.makeText(getApplicationContext(),"Please Enter a Number",Toast.LENGTH_LONG).show();
              }

              else {
                  databaseReference.orderByChild("lotterynum").equalTo(text).addValueEventListener(new ValueEventListener() {
                      @RequiresApi(api = Build.VERSION_CODES.M)
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          if(dataSnapshot.exists()){
                              databaseReference2.child((Prevalent.currentOnlineUser.getEmail())).child("lotterynum").setValue(text);
                              editText.setText("");
                              startActivity(new Intent(CopyitemActivity.this,MainActivity.class));
                          }
                          else {
                              final AlertDialog alertDialog;
                              AlertDialog.Builder builder = new AlertDialog.Builder(CopyitemActivity.this);
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
