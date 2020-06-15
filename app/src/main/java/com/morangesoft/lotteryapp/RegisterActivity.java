package com.morangesoft.lotteryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private TextView sign_in;
    private Button createAccountButton;
    private EditText InputName, InputEmail, InputPassword;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sign_in = findViewById(R.id.sign_in);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        createAccountButton = findViewById(R.id.register_btn);
        InputName = findViewById(R.id.register_username_input);
        InputEmail = findViewById(R.id.register_phone_number_input);
        InputPassword = findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String name = InputName.getText().toString();
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            InputName.setError("Name Field can't be Blank");
            return;
        } else if (TextUtils.isEmpty(email)) {
            InputEmail.setError("Phone Field can't be Blank");
            return;
        } else if (TextUtils.isEmpty(password)) {
            InputPassword.setError("Password Field can't be Blank");
            return;
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please Wait, While we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatephoneNumber(name, email, password);
        }
    }

    private void validatephoneNumber(final String name, final String email, final String password) {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!(dataSnapshot.child("Users").child(email).exists())){
                            HashMap<String,Object> userDataMap = new HashMap<>();
                            userDataMap.put("email",email);
                            userDataMap.put("password",password);
                            userDataMap.put("name",name);

                            Rootref.child("Users").child(email).updateChildren(userDataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,"Congratulations , Your account is created",Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();

                                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                loadingBar.dismiss();
                                                Toast.makeText(RegisterActivity.this,"NetWork error! Please, try again",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"This "+email+"Already exists", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Toast.makeText(RegisterActivity.this,"Please try again using another Phone Number",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


}
