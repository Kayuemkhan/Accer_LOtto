package com.morangesoft.lotteryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.morangesoft.lotteryapp.Prevalent.Prevalent;

import java.util.HashMap;

public class ModificarClave extends AppCompatActivity {

    private EditText currentpass,ChangePass,ChangePass2;
    private Button checkButton;
    private DatabaseReference databaseReference;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_clave);

        Toolbar toolbar = findViewById(R.id.toolbarModificarClave);
        toolbar.setTitle("Modificar Clave");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentpass= findViewById(R.id.current_password);
        ChangePass = findViewById(R.id.change_password);
        ChangePass2 = findViewById(R.id.change_password2);
        checkButton = findViewById(R.id.checkButton);
        loadingBar = new ProgressDialog(this);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check();
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void check() {
        final String currentpas = currentpass.getText().toString();
        final String changepas = ChangePass.getText().toString();
        final String changepas2 = ChangePass2.getText().toString();

        if(currentpas.isEmpty()){
            currentpass.setError("This Field can't be Blank");
            return;
        }
        else if(changepas.isEmpty()){
            ChangePass.setError("This Filed Can't be Blank");
        }
        else if(changepas2.isEmpty()){
            ChangePass.setError("This Filed Can't be Blank");
        }
        else{
            databaseReference.orderByChild("email").equalTo(Prevalent.currentOnlineUser.getEmail()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        //ChangePass.setVisibility(View.VISIBLE);

                        HashMap<String,Object> userDataMap = new HashMap<>();
                        userDataMap.put("email",Prevalent.currentOnlineUser.getEmail());
                        userDataMap.put("password",changepas);
                        userDataMap.put("name",Prevalent.currentOnlineUser.getName());
                        databaseReference.child(Prevalent.currentOnlineUser.getEmail()).updateChildren(userDataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(ModificarClave.this,"Congratulations , Your account is created",Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();

                                            Intent intent = new Intent(ModificarClave.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            loadingBar.dismiss();
                                            Toast.makeText(ModificarClave.this,"NetWork error! Please, try again",Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
}
