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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nahidd.dholaimama.adapter.CustomerInfoAdapter;
import com.nahidd.dholaimama.model.CustomerInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout customerForm, offer;

    private TextView TotalCustomerNumber, TotalUsersNumber,daily_total;

    private Button showBtn;

    private CustomerInfoAdapter customerInfoAdapter;
    private FirebaseFirestore db;


    int nb = 0;
    int today_survey=0;
    int total_user = 0;

    private ArrayList<CustomerInfo> coursesArrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //getCurrentDate();
      //  validPhoneNumber();
        customerForm = findViewById(R.id.gotoCustomerFormLL);
        offer = findViewById(R.id.offerLL);


        TotalCustomerNumber = findViewById(R.id.total_customer);
        TotalUsersNumber = findViewById(R.id.total_user);
        daily_total = findViewById(R.id.daily_total);





        ////////total Customer
        db = FirebaseFirestore.getInstance();
        coursesArrayList = new ArrayList<>();

          getCurrentDate();
        //  getMonthlySurvey();

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

//    private void getMonthlySurvey() {
//
//        db.collection("Customer")
//                .whereEqualTo("currentDate", currentDate)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                today_survey = today_survey+1;
//                            }
//                            String today_serv = String.valueOf(today_survey);
//                            daily_total.setText(today_serv);
//
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }


    ////////////////////currentDate////////////////////////////

    public String currentDate() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // to set the current date as by default
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        return date;
    }

    ////////////////////currentDate////////////////////////////

    //////////////// Single Data Get System Firebase Fire store Daily Survey and Current Date//////////////////

    public void getCurrentDate() {

        String currentDate = currentDate();

        db.collection("Customer")
                .whereEqualTo("currentDate", currentDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                today_survey = today_survey+1;
                            }
                            String today_serv = String.valueOf(today_survey);
                            daily_total.setText(today_serv);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //////////////// Single Data Get System Firebase Fire store Daily Survey and Current Date//////////////////


    }




}
