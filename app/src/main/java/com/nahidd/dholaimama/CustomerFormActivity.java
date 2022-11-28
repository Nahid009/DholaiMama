package com.nahidd.dholaimama;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
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
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nahidd.dholaimama.model.CustomerInfo;
import com.nahidd.dholaimama.model.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CustomerFormActivity extends AppCompatActivity {

    private ImageView captureImage;
    private EditText customer_name, customer_phone_number, customer_address, customer_totalJobHolder, customer_monthlyLandryCost;
    private Button okButton;
    private CheckBox interested;

    /////////location
    private LocationRequest locationRequest;
    public double latitude;
    public double longitude;
    /////////location

    String lat;
    String provider;

    private boolean unique_number = false;

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

      //  validPhoneNumber();


        ////////////location
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        latitude = 0.0;
        longitude = 0.0;

        ////////////location


        db = FirebaseFirestore.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String customer_id = firebaseUser.getUid();


        /////////check valid phone number
        boolean validNumber = validPhoneNumber();
        /////////check valid phone number

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

                } else if (TextUtils.isEmpty(address)) {

                        Toast.makeText(CustomerFormActivity.this, "Please Enter Valid address!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(customer_totalJobHolder.getText().toString())) {

                    Toast.makeText(CustomerFormActivity.this, "Please Enter Valid Total Job Holder!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(customer_monthlyLandryCost.getText().toString())) {

                    Toast.makeText(CustomerFormActivity.this, "Please Enter Valid Monthly Landry Cost!", Toast.LENGTH_SHORT).show();

                } else {

                    if (validNumber){
                        Toast.makeText(CustomerFormActivity.this, "Phone number is already used!", Toast.LENGTH_SHORT).show();
                    }else {

                        UserInfo userInfo = new UserInfo();
                        String userId = userInfo.getUser_id();

                        ///////////////////////////Location////////////////////////////////

                        getCurrentLocation();

                        //////////////////////////Location//////////////////////////////

                        //////////////////////// currentDate //////////////////////////

                        String currentDate = currentDate();

                        //////////////////////// currentDate //////////////////////////

                        addInfo(customer_id, name, address, phone_number, userId, currentDate, latitude, longitude, isInterested);

                        Intent intent = new Intent(CustomerFormActivity.this, SuccessActivity.class);
                        startActivity(intent);

                    }
                }
            }

        });

    }


    private void addInfo(String customer_id, String name, String address, String phone_number, String user_id, String currentDate, double lati, double longi, boolean isChecked) {


        CollectionReference dbCourses = db.collection("Customer");
        Log.d("phone",phone_number);


        CustomerInfo customerInfo = new CustomerInfo(customer_id, name, address, phone_number, user_id, currentDate, lati, longi, isChecked);
        Log.d("phone",customerInfo.getCustomer_phone_number());
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

    ////////////////////currentDate////////////////////////////

    public String currentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // to set the current date as by default
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        return date;
    }
    ////////////////////currentDate////////////////////////////

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    getCurrentLocation();

                } else {

                    turnOnGPS();
                }
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(CustomerFormActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(CustomerFormActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(CustomerFormActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0) {

                                        int index = locationResult.getLocations().size() - 1;
                                        latitude = locationResult.getLocations().get(index).getLatitude();
                                        longitude = locationResult.getLocations().get(index).getLongitude();

                                        Log.d("lati : ", String.valueOf(latitude));
                                        Log.d("longi : ", String.valueOf(longitude));

                                        // AddressText.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void turnOnGPS() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(CustomerFormActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(CustomerFormActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

/////////////// UniquePhoneNumber ///////////////////////

    public boolean validPhoneNumber(){

        String phoneNumber;

        phoneNumber = customer_phone_number.getText().toString();

        db.collection("Customer")
                .whereEqualTo("customer_phone_number", phoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        unique_number = true;
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        unique_number = false;
                    }
                });

        return unique_number;

    }
    /////////////// UniquePhoneNumber ///////////////////////


}