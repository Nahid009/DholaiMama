package com.nahidd.dholaimama;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nahidd.dholaimama.model.CustomerInfo;

public class MainActivity extends AppCompatActivity {

    private LinearLayout customerForm, offer;

    private Button showBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerForm = findViewById(R.id.gotoCustomerFormLL);
        offer = findViewById(R.id.offerLL);



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