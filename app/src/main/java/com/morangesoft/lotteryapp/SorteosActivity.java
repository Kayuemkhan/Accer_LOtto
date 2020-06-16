package com.morangesoft.lotteryapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SorteosActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    ArrayList<String> primeraArray = new ArrayList<>();
    ArrayList<String> segundaArray = new ArrayList<>();
    ArrayList<String> terceraArray = new ArrayList<>();

    RecyclerView sorteosRecyclerView;
    ImageView sercher;
    TextView mgs;

    private EditText datePicker;
    private final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sorteos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sorteosRecyclerView = findViewById(R.id.sorteosLayoutID);
        sercher = findViewById(R.id.searchreghsIcone);
        mgs = findViewById(R.id.searchreghsText);
        loadSorteosData();

        datePicker = findViewById(R.id.datepickersorteos);
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SorteosActivity.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void loadSorteosData() {
        DatabaseReference reference = database.child("Sorteos").child("Sorteos");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                primeraArray.clear();
                segundaArray.clear();
                terceraArray.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    primeraArray.add(dataSnapshot1.child("Primera").getValue().toString());
                    segundaArray.add(dataSnapshot1.child("Segunda").getValue().toString());
                    terceraArray.add(dataSnapshot1.child("Tercera").getValue().toString());
                }
                if (!primeraArray.isEmpty()){
                    sercher.setVisibility(View.INVISIBLE);
                    mgs.setVisibility(View.INVISIBLE);
                }
                sorteosRecyclerView.setAdapter(new SorteosAdapter(getApplicationContext(), primeraArray, segundaArray, terceraArray));
                sorteosRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        datePicker.setText(sdf.format(myCalendar.getTime()));
    }


}
