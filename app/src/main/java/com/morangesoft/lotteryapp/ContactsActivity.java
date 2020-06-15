package com.morangesoft.lotteryapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference reference = database.child("Contatcs");
    ArrayList<String> nameArray = new ArrayList<>();
    ArrayList<String> phoneArray = new ArrayList<>();

    FloatingActionButton floatingButton;
    RecyclerView contactRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        Toolbar toolbar = findViewById(R.id.contactstoolbar);
        toolbar.setTitle("Contacts");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contactRecyclerView = findViewById(R.id.contactRecyclerViewID);
        loadData();
        floatingButton = findViewById(R.id.floating_action_button);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.sample_alart_dialog, null);
                builder.setView(mView);

                final EditText nameEditText = mView.findViewById(R.id.nameEditTextId);
                final EditText phoneEditText = mView.findViewById(R.id.phoneEditTextID);
                Button save = mView.findViewById(R.id.save);
                Button cancel = mView.findViewById(R.id.cancell);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = nameEditText.getText().toString().trim();
                        String phone = phoneEditText.getText().toString().trim();
                        ContactClass contactClass = new ContactClass(name, phone);
                        FirebaseDatabase.getInstance().getReference("Contatcs")
                                .child(phone).setValue(contactClass);
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    private void loadData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phoneArray.clear();
                nameArray.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    nameArray.add(dataSnapshot1.child("Name").getValue().toString());
                    phoneArray.add(dataSnapshot1.child("Phone").getValue().toString());
                }
                contactRecyclerView.setAdapter(new ContactAdapter(getApplicationContext(), nameArray, phoneArray));
                contactRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
