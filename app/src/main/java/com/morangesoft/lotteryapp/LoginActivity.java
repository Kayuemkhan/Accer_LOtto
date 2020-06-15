package com.morangesoft.lotteryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.morangesoft.lotteryapp.Prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private TextView signup;
    private FirebaseUser firebaseUser = null;

    private FirebaseAuth firebaseAuth;

    private EditText InputNumber, InputPassword;
    private Button LoginButton;
    ProgressDialog loadingBar1;
    private String parentDbName = "Users";

    @Override
    protected void onStart() {
        super.onStart();
        if (!(firebaseUser == null)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);
        signup = findViewById(R.id.sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });


        LoginButton = findViewById(R.id.login_btn);
        InputNumber = findViewById(R.id.login_phone_number_input);
        InputPassword = findViewById(R.id.login_password_input);
        loadingBar1 = new ProgressDialog(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String phone = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            InputNumber.setError("Phone Field can't be Blank");
            return;
        } else if (TextUtils.isEmpty(password)) {
            InputPassword.setError("Password Field can't be Blank");
            return;
        } else {
            loadingBar1.setTitle("Login Account");
            loadingBar1.setMessage("Please Wait, While we are checking the credentials");
            loadingBar1.setCanceledOnTouchOutside(false);
            loadingBar1.show();
            AllowAccssAccount(phone, password);
        }
    }

    private void AllowAccssAccount(final String phone, final String password) {

        Paper.book().write(Prevalent.UserPhoneKey,phone);
        Paper.book().write(Prevalent.UserPasswordKey,password);
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getEmail().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {
                            loadingBar1.dismiss();
                            Prevalent.currentOnlineUser = usersData;
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        loadingBar1.dismiss();
                        Toast.makeText(LoginActivity.this, "password is not Correct", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Account With this" + phone + " number do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar1.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingBar1.dismiss();
            }
        });
    }


}
