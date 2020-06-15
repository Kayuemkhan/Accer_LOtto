package com.morangesoft.lotteryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SorteosInput extends AppCompatActivity {
    private EditText primera, segunda,tercera;
    private Button submitsorteos;
    private DatabaseReference databaseReference;
    private ProgressDialog loadingbar1;
    private String date  = java.time.LocalDate.now().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteos_input);
        Toolbar toolbar = findViewById(R.id.sorteos_input);
        toolbar.setTitle("Sorteos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        primera = findViewById(R.id.primera_input);
        segunda = findViewById(R.id.segunda_input);
        tercera = findViewById(R.id.tercera_input);

        submitsorteos = findViewById(R.id.sorteos_submit);
        submitsorteos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddata();
            }
        });

        loadingbar1 = new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Sorteos");
    }

    private void adddata() {
        String pri = primera.getText().toString();
        String seg = segunda.getText().toString();
        String ter = tercera.getText().toString();

        if(TextUtils.isEmpty(pri)){
            primera.setError("This field can't be blanked");
        }
        else if(TextUtils.isEmpty(seg)){
            segunda.setError("This field can't be blanked");
        }
        else if(TextUtils.isEmpty(ter)){
            tercera.setError("This field can't be blanked");
        }
        else {
            loadingbar1.setTitle("Setting Data");
            loadingbar1.setMessage("Please Wait...");
            loadingbar1.setCanceledOnTouchOutside(false);
            loadingbar1.show();
            adddatatoFirebase(pri, seg,ter);
        }


    }

    private void adddatatoFirebase(String pri, String seg, String ter) {
        HashMap<String,String> hashMap = new HashMap<>();

        hashMap.put("Primera",pri);
        hashMap.put("Segunda",seg);
        hashMap.put("Tercera",ter);
        databaseReference.child(databaseReference.getKey()).child(date).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               if(task.isComplete()){
                   loadingbar1.dismiss();
                   startActivity(new Intent(SorteosInput.this, MainActivity.class));
                   finish();
               }

            }
        });
    }
}