package com.morangesoft.lotteryapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.morangesoft.lotteryapp.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int CONTENT_VIEW_ID = 10101010;
    public static int i = 1;
    DrawerLayout drawerLayout;
    Fragment fragment = null;
    private Button addbutton;
    private EditText et1, et2, et3;
    private DatabaseReference databaseReference;
    private ListView listView;
    private RecyclerView recyclerView;
    ArrayList<DatasetFireabase> arrayList;
    private RecyclerView.Adapter<FirebaseViewHolder.ViewHolder> adapter;
    private TextView userName;
    private CircleImageView circleImageView;
    public String checker = "";
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private int RequestCode = 435;
    private DatabaseReference databaseReference2;
    private static final int PERMISSION_CODE = 1000;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permission, PERMISSION_CODE);
        }


        addbutton = findViewById(R.id.addbutton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                addbutton1();

            }
        });

        et1 = findViewById(R.id.p1);
        et2 = findViewById(R.id.p2);
        et3 = findViewById(R.id.p3);


        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.username_g);
        userName.setText(Prevalent.currentOnlineUser.getName());

        circleImageView = headerView.findViewById(R.id.profile_picture);

        // Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.pp).into(circleImageView);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Lottery");
        databaseReference.keepSynced(true);

        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Sold Tickets");

        recyclerView = findViewById(R.id.recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        arrayList = new ArrayList<>();


//       circleImageView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent intent = new Intent();
//               intent.setType("image/*");
//               intent.setAction(Intent.ACTION_GET_CONTENT);
//               startActivityForResult(intent,RequestCode);
//
//
//           }
//       });

        // options = new FirebaseRecyclerOptions.Builder<DatasetFireabase>().setQuery(databaseReference,DatasetFireabase.class).build();
    }


   // @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode ==RESULT_OK && data!=null){
//            //CropImage.ActivityResult result= CropImage.getActivityResult(data);
//            if (requestCode == RequestCode
//                    && resultCode == RESULT_OK
//                    && data != null
//                    && data.getData() != null) {
//
//                // Get the Uri of data
//                imageUri = data.getData();
//                Toast.makeText(getApplicationContext(),"Uploading...",Toast.LENGTH_LONG).show();
//                uploadImage();
//            }
//        }
//
//
//        else {
//            Toast.makeText(this,"Error , try again", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this,MainActivity.class));
//            finish();
//        }
//    }
//    private void uploadImage() {
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Update Profile");
//        progressDialog.setMessage("Please wait, While we are updating your account information");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//
//        if(imageUri != null){
//            final StorageReference fileRef = storageProfilePrictureRef
//                    .child(Prevalent.currentOnlineUser.getEmail()+ ".jpg");
//            uploadTask = fileRef.putFile(imageUri);
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//                    if(!task.isSuccessful()){
//                        throw  task.getException();
//                    }
//                    return fileRef.getDownloadUrl();
//                }
//            })
//                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if(task.isSuccessful()){
//                                Uri downloadUrl = task.getResult();
//                                assert downloadUrl != null;
//                                myUrl = downloadUrl.toString();
//                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
//
//                                HashMap<String,Object> userMap = new HashMap<>();
//
//                                userMap.put("image",myUrl);
//                                ref.child(Prevalent.currentOnlineUser.getEmail()).updateChildren(userMap);
//
//                                progressDialog.dismiss();
//                                startActivity(new Intent(MainActivity.this,MainActivity.class));
//                                Toast.makeText(MainActivity.this,"Profile Info Updated Successfully",Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                            else {
//                                progressDialog.dismiss();
//                                Toast.makeText(MainActivity.this,"Error ! ",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//        else {
//            Toast.makeText(this,"Image is not Selected",Toast.LENGTH_SHORT).show();
//
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== R.id.whatsapp){
            String number = "+51990148228" ;
            String url = "https://api.whatsapp.com/send?phone="+number;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
        if (item.getItemId()== R.id.mail){
            String to = "mq@morangesoft.com";
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:mq@morangesoft.com"));
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));

        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id ==R.id.copy){
//            startActivity(new Intent(this,CopyitemActivity.class));
//            finish();
            final CharSequence[] items =  {"Numero de ticket", "Codigo QR"};
            final AlertDialog.Builder aleart= new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Copiar Ticket")
                    .setItems(items, new DialogInterface.OnClickListener()
                    {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            switch(which)
                            {
                                case 0:
                                    dialog.dismiss();
                                    startActivity(new Intent(MainActivity.this,CopyitemActivity.class));

                                    break;
                                case 1:
                                    dialog.dismiss();
                                    //                                    final AlertDialog alertDialog1;
//                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//                                    alertDialog1 = builder1.create();
//                                    final TextView title1 = new TextView(getApplicationContext());
//                                    title1.setText("Copy");
//                                    title1.setBackgroundColor(getColor(R.color.Black));
//                                    title1.setPadding(10, 15, 15, 10);
//                                    title1.setGravity(Gravity.CENTER);
//                                    title1.setTextColor(Color.WHITE);
//                                    title1.setTextSize(22);
//                                    alertDialog1.setCustomTitle(title1);
//                                    alertDialog1.setMessage("No se encontró ninguna impresora");
//                                    alertDialog1.setButton("Ok", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            alertDialog1.dismiss();
//                                        }
//                                    });
//                                    alertDialog1.show();

                                    Intent intent = new Intent(MainActivity.this,QRActivity2.class);
                                    startActivity(intent);
                        }}
                    });
                           aleart.create().show();

        }
        if (id ==R.id.cancel){

            final CharSequence[] items =  {"Numero de ticket", "Codigo QR"};

            AlertDialog.Builder aleart= new AlertDialog.Builder(this)
                    .setTitle("Copiar Ticket")
                    .setItems(items, new DialogInterface.OnClickListener()
                    {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            switch(which)
                            {
                                case 0:
                                    dialog.dismiss();
                                    startActivity(new Intent(MainActivity.this,CancelActivity.class));
//                                    final AlertDialog alertDialog;
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                    alertDialog = builder.create();
//                                    final TextView title = new TextView(getApplicationContext());
//                                    title.setText("Copy");
//                                    title.setBackgroundColor(getColor(R.color.Black));
//                                    title.setPadding(10, 15, 15, 10);
//                                    title.setGravity(Gravity.CENTER);
//                                    title.setTextColor(Color.WHITE);
//                                    title.setTextSize(22);
//                                    alertDialog.setCustomTitle(title);
//                                    alertDialog.setMessage("No se encontró ninguna impresora");
//                                    alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                                    alertDialog.show();

                                    break;
                                  case 1:
                                      dialog.dismiss();
//                                      final AlertDialog alertDialog1;
//                                      AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//                                      alertDialog1 = builder1.create();
//                                      final TextView title1 = new TextView(getApplicationContext());
//                                      title1.setText("Copy");
//                                      title1.setBackgroundColor(getColor(R.color.Black));
//                                      title1.setPadding(10, 15, 15, 10);
//                                      title1.setGravity(Gravity.CENTER);
//                                      title1.setTextColor(Color.WHITE);
//                                      title1.setTextSize(22);
//                                      alertDialog1.setCustomTitle(title1);
//                                      alertDialog1.setMessage("No se encontró ninguna impresora");
//                                      alertDialog1.setButton("Ok", new DialogInterface.OnClickListener() {
//                                          @Override
//                                          public void onClick(DialogInterface dialog, int which) {
//                                              alertDialog1.dismiss();
//                                          }
//                                      });
//                                      alertDialog1.show();
                                 Intent intent = new Intent(MainActivity.this,QRActivity.class);
                              startActivity(intent);


                            }
                        }
                    });
            aleart.create().show();

        }


        if (id ==R.id.tickets){

           startActivity(new Intent(this,TicketActivity.class));


        }
        if (id ==R.id.sorteos){

            startActivity(new Intent(this,SorteosActivity.class));

        }
        if (id ==R.id.cash_match){

            startActivity(new Intent(this,CashMatchActivity.class));

        }
        if (id ==R.id.notificationes){
        }

        if (id ==R.id.Contacts){

            startActivity(new Intent(this,ContactsActivity.class));

        }
        if (id== R.id.modificar_clave){
            startActivity(new Intent(MainActivity.this,ModificarClave.class));
        }
        if (id == R.id.exit){
            Paper.book().destroy();
            AlertDialog.Builder aleartDialog = new AlertDialog.Builder(this);
            aleartDialog.setTitle("Exit");
            aleartDialog.setMessage("Do you want to exit?");
            aleartDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            aleartDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            aleartDialog.create();
            aleartDialog.show();
        }
        if (id == R.id.info){
            AlertDialog.Builder aleart = new AlertDialog.Builder(this);
            aleart.setTitle("About");
            aleart.setMessage(R.string.alert_msg);
            aleart.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            aleart.create();
            aleart.show();
        }
        if (id == R.id.admin){
            startActivity(new Intent(this,SelectClass.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addbutton1() {
        final String lotterynum = et2.getText().toString().trim();
        final String pr = "QR";
        final String numero = et1.getText().toString().trim();
        final String valor = et3.getText().toString().trim();

        databaseReference.orderByChild("lotterynum").equalTo(lotterynum).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            //et1.setText("yes");

                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Date date = new Date();
                            HashMap<String,String> soldTickets = new HashMap<>();

                            soldTickets.put("Tikcet",lotterynum);
                            soldTickets.put("Hora",java.time.LocalDate.now().toString());
                            soldTickets.put("Amount",valor);

                            databaseReference2.child(Prevalent.currentOnlineUser.getEmail()).child(databaseReference2.push().getKey()).setValue(soldTickets);


                            if (arrayList.isEmpty()) {
                                arrayList.add(new DatasetFireabase(lotterynum, pr, numero, valor));
                                adapter = new FirebaseViewHolder(arrayList, getApplicationContext());
                                recyclerView.setAdapter(adapter);

                            } else {
                                int insertIndex = 1;
                                arrayList.add(insertIndex, new DatasetFireabase(lotterynum, pr, numero, valor));
                                //adapter = new FirebaseViewHolder(arrayList, getApplicationContext());
                                //recyclerView.setAdapter(adapter);
                                adapter.notifyItemInserted(insertIndex);
                            }
                        } else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                            TextView title = new TextView(getApplicationContext());
                            title.setText("Error");
                            title.setBackgroundColor(getColor(R.color.Black));
                            title.setPadding(10, 15, 15, 10);
                            title.setGravity(Gravity.CENTER);
                            title.setTextColor(Color.WHITE);
                            title.setTextSize(22);
                            alertDialog.setCustomTitle(title);

                            LinearLayout linearLayout = new LinearLayout(getApplicationContext());

                            TextView textView = new TextView(getApplicationContext());
                            textView.setText("Wrong Lottery Number");
                            textView.setPadding(10, 15, 15, 10);
                            textView.setGravity(Gravity.CENTER);
                            textView.setTextSize(15);
                            linearLayout.addView(textView);

                            alertDialog.setView(linearLayout);

                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.create();
                            alertDialog.show();
                        }
                    }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                TextView title = new TextView(getApplicationContext());
                title.setText("Error");
                title.setBackgroundColor(getColor(R.color.Black));
                title.setPadding(10, 15, 15, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.WHITE);
                title.setTextSize(22);
                alertDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getApplicationContext());

                TextView textView = new TextView(getApplicationContext());
                textView.setText("Wrong Lottery Number");
                textView.setPadding(10, 15, 15, 10);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(15);
                linearLayout.addView(textView);

                alertDialog.setView(linearLayout);

                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.create();
                alertDialog.show();
            }

        });
  }

    @Override
    protected void onStop() {
        super.onStop();
        i=0;
    }
}

