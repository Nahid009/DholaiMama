package com.nahidd.dholaimama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nahidd.dholaimama.adapter.CustomerInfoAdapter;
import com.nahidd.dholaimama.model.CustomerInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout customerForm, offer;

    private TextView TotalCustomerNumber,TotalUsersNumber;

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

        customerForm = findViewById(R.id.gotoCustomerFormLL);
        offer = findViewById(R.id.offerLL);


        TotalCustomerNumber = findViewById(R.id.total_customer);
        TotalUsersNumber = findViewById(R.id.total_user);



//        number = String.valueOf(coursesArrayList.size());
//        TotalCustomerNumber.setText(number);



        ////////total Customer
        db = FirebaseFirestore.getInstance();
        coursesArrayList = new ArrayList<>();


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
                Intent i = new Intent(MainActivity.this,CustomerFormActivity.class);
                startActivity(i);
            }
        });
        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,OfferActivity.class);
                startActivity(i);
            }
        });
    }
}