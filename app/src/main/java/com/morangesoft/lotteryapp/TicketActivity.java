package com.morangesoft.lotteryapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.morangesoft.lotteryapp.Prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TicketActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
   private   Spinner spinner;
    private String[] country = new String[]{"All", "Impresora", "Whatsapp", "SMS", "None", "Premiados"};
    private EditText datePicker;
    private final Calendar myCalendar = Calendar.getInstance();
    private RecyclerView ticketsRecyclerView;
    String currentUserNumber;

    ImageView sercher;
    TextView mgsText;

    ArrayList<String> amountArray = new ArrayList<>();
    ArrayList<String> horaArray = new ArrayList<>();
    ArrayList<String> ticketsArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        sercher = findViewById(R.id.searchres);
        mgsText= findViewById(R.id.mgsTextView);
        ticketsRecyclerView = findViewById(R.id.ticketsLayoutID);
        currentUserNumber = Prevalent.currentOnlineUser.getEmail();

        loadTicketsData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tickets");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter <CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,country);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        datePicker = findViewById(R.id.datepicker);
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
                new DatePickerDialog(TicketActivity.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void loadTicketsData() {
        DatabaseReference reference = database.child("Sold Tickets").child(currentUserNumber);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                amountArray.clear();
                horaArray.clear();
                ticketsArray.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    amountArray.add(dataSnapshot1.child("Amount").getValue().toString());
                    horaArray.add(dataSnapshot1.child("Hora").getValue().toString());
                    ticketsArray.add(dataSnapshot1.child("Tikcet").getValue().toString());
                }
                if (!amountArray.isEmpty()){
                    sercher.setVisibility(View.INVISIBLE);
                    mgsText.setVisibility(View.INVISIBLE);
                }
                ticketsRecyclerView.setAdapter(new TicketsAdapter(getApplicationContext(), amountArray, horaArray, ticketsArray));
                ticketsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
