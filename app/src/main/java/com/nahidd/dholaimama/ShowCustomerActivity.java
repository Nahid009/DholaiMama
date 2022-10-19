package com.nahidd.dholaimama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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

public class ShowCustomerActivity extends AppCompatActivity {

    private RecyclerView courseRV;
    private ArrayList<CustomerInfo> coursesArrayList;
    private CustomerInfoAdapter customerInfoAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customer);

        courseRV = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idProgressBar);

        db = FirebaseFirestore.getInstance();

        coursesArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(this));

        customerInfoAdapter = new CustomerInfoAdapter(coursesArrayList, this);

        courseRV.setAdapter(customerInfoAdapter);

        db.collection("Customer").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                CustomerInfo c = d.toObject(CustomerInfo.class);

                                coursesArrayList.add(c);


                            }

                            customerInfoAdapter.notifyDataSetChanged();

                        } else {

                            loadingPB.setVisibility(View.GONE);

                            Toast.makeText(ShowCustomerActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(ShowCustomerActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}