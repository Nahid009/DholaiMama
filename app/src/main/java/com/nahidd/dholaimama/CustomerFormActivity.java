package com.nahidd.dholaimama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nahidd.dholaimama.model.CustomerInfo;
import com.nahidd.dholaimama.model.UserInfo;

import java.util.List;


public class CustomerFormActivity extends AppCompatActivity implements LocationListener {

    private ImageView captureImage;
    private EditText customer_name, customer_phone_number, customer_address, customer_totalJobHolder, customer_monthlyLandryCost;
    private Button okButton;
    private CheckBox interested;

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    //  private TextView txtLat;


    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;


    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;


    private boolean isInterested;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);

        customer_name = findViewById(R.id.customer_name);
        customer_address = findViewById(R.id.customer_address);
        customer_phone_number = findViewById(R.id.customer_phone_num);
        customer_totalJobHolder = findViewById(R.id.totalJobHolder);
        customer_monthlyLandryCost = findViewById(R.id.monthlyLandryCost);
        okButton = findViewById(R.id.okButton);
        interested = findViewById(R.id.checkedInterested);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        db = FirebaseFirestore.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String customer_id = firebaseUser.getUid();


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name, address, phone_number;

                name = customer_name.getText().toString();
                address = customer_address.getText().toString();
                phone_number = customer_phone_number.getText().toString();

                int lenth = phone_number.length();


                if (interested.isChecked()) {
                    isInterested = true;
                } else {
                    isInterested = false;
                }


                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(CustomerFormActivity.this, "Please Enter Valid Name! ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone_number)) {
                    Toast.makeText(CustomerFormActivity.this, "Please Enter Valid Phone Number!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address) && lenth != 11) {
                    Toast.makeText(CustomerFormActivity.this, "Please Enter Valid address!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(customer_totalJobHolder.getText().toString())) {
                    Toast.makeText(CustomerFormActivity.this, "Please Enter Valid Total Job Holder!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(customer_monthlyLandryCost.getText().toString())) {
                    Toast.makeText(CustomerFormActivity.this, "Please Enter Valid Monthly Landry Cost!", Toast.LENGTH_SHORT).show();
                } else {
                    UserInfo userInfo = new UserInfo();
                    String userId = userInfo.getUser_id();


                    addInfo(customer_id, name, address, phone_number, userId, "", isInterested);

                    Intent intent = new Intent(CustomerFormActivity.this, SuccessActivity.class);
                    startActivity(intent);
                }


            }

        });

    }


    private void addInfo(String customer_id, String name, String address, String phone_number, String user_id, String location, boolean isChecked) {


        CollectionReference dbCourses = db.collection("Customer");


        CustomerInfo customerInfo = new CustomerInfo(customer_id, name, address, phone_number, user_id, location, isChecked);


        dbCourses.add(customerInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(CustomerFormActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CustomerFormActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        // txtLat = (TextView) findViewById(R.id.textview1);
        //  txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }


    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
        Log.d("Latitude", "disable");
    }
}