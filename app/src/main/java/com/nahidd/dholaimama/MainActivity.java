package com.nahidd.dholaimama;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nahidd.dholaimama.adapter.CustomerInfoAdapter;
import com.nahidd.dholaimama.model.CustomerInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LinearLayout customerForm, offer;

    private TextView TotalCustomerNumber, TotalUsersNumber;

    private Button showBtn;

    private CustomerInfoAdapter customerInfoAdapter;
    private FirebaseFirestore db;


    int nb = 0;
    int total_user = 0;

    private ArrayList<CustomerInfo> coursesArrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //getCurrentDate();
        customerForm = findViewById(R.id.gotoCustomerFormLL);
        offer = findViewById(R.id.offerLL);


        TotalCustomerNumber = findViewById(R.id.total_customer);
        TotalUsersNumber = findViewById(R.id.total_user);


//        number = String.valueOf(coursesArrayList.size());
//        TotalCustomerNumber.setText(number);


        ////////total Customer
        db = FirebaseFirestore.getInstance();
        coursesArrayList = new ArrayList<>();
        collectionCities();
      //  getCurrentDate();

        customerInfoAdapter = new CustomerInfoAdapter(coursesArrayList, this);

        db.collection("Customer").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                CustomerInfo c = d.toObject(CustomerInfo.class);
                                coursesArrayList.add(c);

                                nb = nb + 1;

                            }
                            String totalCustomer = String.valueOf(nb);
                            TotalCustomerNumber.setText(totalCustomer);

                            customerInfoAdapter.notifyDataSetChanged();

                        } else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });


        ////////total Customer

        /////////total user
        db.collection("Users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                Log.d("cutomerlist", d.toString());
                                CustomerInfo c = d.toObject(CustomerInfo.class);
                                coursesArrayList.add(c);

                                total_user = total_user + 1;

                            }
                            String totalUser = String.valueOf(total_user);
                            TotalUsersNumber.setText(totalUser);

                            customerInfoAdapter.notifyDataSetChanged();

                        } else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });
        /////////total user

        /////////new added
        showBtn = findViewById(R.id.showBtn);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowCustomerActivity.class);
                startActivity(intent);
            }
        });
        /////////new added


        customerForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CustomerFormActivity.class);
                startActivity(i);
            }
        });
        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, OfferActivity.class);
                startActivity(i);
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

    //////////////// Single Data Get System Firebase Firestore//////////////////

    public void getCurrentDate() {
        final int[] today_survey = {0};
        String currentDate = currentDate();

        com.google.firebase.firestore.Query capitalCities = db.collection("Customer").whereEqualTo("currentDate", "09/11/2022");

        db.collection("Customer")
                .whereEqualTo("currentDate", "14/11/2022")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            today_survey[0] = today_survey[0] + 1;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //  Log.d(TAG, "Cities Collection is ok");
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error getting documents: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Log.d("NahidHasan", String.valueOf(today_survey.length));


    }


    //////////////// Single Data Get System Firebase Firestore//////////////////


    ///////// Perform simple and compound queries in Cloud Firestore ////////
    public void collectionCities() {
        CollectionReference cities = db.collection("cities");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "San Francisco");
        data1.put("state", "CA");
        data1.put("country", "USA");
        data1.put("capital", false);
        data1.put("population", 860000);
        data1.put("regions", Arrays.asList("west_coast", "norcal"));
        cities.document("SF").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "Los Angeles");
        data2.put("state", "CA");
        data2.put("country", "USA");
        data2.put("capital", false);
        data2.put("population", 3900000);
        data2.put("regions", Arrays.asList("west_coast", "socal"));
        cities.document("LA").set(data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("name", "Washington D.C.");
        data3.put("state", null);
        data3.put("country", "USA");
        data3.put("capital", true);
        data3.put("population", 680000);
        data3.put("regions", Arrays.asList("east_coast"));
        cities.document("DC").set(data3);

        Map<String, Object> data4 = new HashMap<>();
        data4.put("name", "Tokyo");
        data4.put("state", null);
        data4.put("country", "Japan");
        data4.put("capital", true);
        data4.put("population", 9000000);
        data4.put("regions", Arrays.asList("kanto", "honshu"));
        cities.document("TOK").set(data4);

        Map<String, Object> data5 = new HashMap<>();
        data5.put("name", "Beijing");
        data5.put("state", null);
        data5.put("country", "China");
        data5.put("capital", true);
        data5.put("population", 21500000);
        data5.put("regions", Arrays.asList("jingjinji", "hebei"));
        cities.document("BJ").set(data5);




        //   com.google.firebase.firestore.Query capitalCities = db.collection("cities").whereEqualTo("capital", true);
        //   Query stateQuery = citiesRef.whereEqualTo("state","CA");
        //   Query populationQuery = citiesRef.whereLessThan("population", 100000);
        // Query nameQuery = citiesRef.whereGreaterThanOrEqualTo("name", "San Francisco");
        // Query noCapitalQuery = citiesRef.whereNotEqualTo("capital",false);



        CollectionReference citiesRef = db.collection("cities");


        Query capitalCities = db.collection("cities").whereEqualTo("capital", true);


        db.collection("cities")
                .whereEqualTo("capital", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Nahid");
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error getting documents: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    ///////// Perform simple and compound queries in Cloud Firestore ////////

}
