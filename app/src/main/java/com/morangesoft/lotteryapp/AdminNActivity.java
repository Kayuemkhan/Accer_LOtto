package com.morangesoft.lotteryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class  AdminNActivity extends AppCompatActivity {
    EditText edtText;
    private Button button;
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_n);

        Toolbar toolbar = findViewById(R.id.toolbaradmin);
        toolbar.setTitle("Admin");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtText = findViewById(R.id.editText);
        button = findViewById(R.id.addButton);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lottery");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }

    private void addData() {
        final String lottery = edtText.getText().toString();
        if (lottery != null){
            //databaseReference.setValue(lottery);
            databaseReference.child(databaseReference.push().getKey()).child("lotterynum").setValue(lottery);
            edtText.setText("");

        }
        else {
            edtText.setError("Please Enter a lottery Number");
        }
    }
}
