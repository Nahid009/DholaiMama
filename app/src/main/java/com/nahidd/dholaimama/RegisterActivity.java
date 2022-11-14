package com.nahidd.dholaimama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nahidd.dholaimama.model.CustomerInfo;
import com.nahidd.dholaimama.model.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {


    private TextView tvLogin;
    private EditText emailET, passwordET, contractEt, nameEt;
    private Button regBtn;

    ///////////////firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    ///////////////firebase


    /////calender
    String dateOfSub;
    /////calender


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        //String user_id = firebaseUser.getUid();  //TODO


        initializeUI();


        /////////calender

        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateOfSub = simpleDateFormat.format(Calendar.getInstance().getTime());

        /////////calender



        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();


                String name = nameEt.getText().toString();
                String email = emailET.getText().toString();

                String contract = contractEt.getText().toString();


                ///calling method...
                addInfo("",name,email,contract,"",dateOfSub);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goToLogin);
            }
        });
    }

    // FireBase New user

    private void registerNewUser() {


        String name, email, password, contract;
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        name = nameEt.getText().toString();
        contract = contractEt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please enter name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contract)) {
            Toast.makeText(getApplicationContext(), "Please enter contract number...", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // addDataToFirestore(name,email,contract);
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void initializeUI() {
        emailET = findViewById(R.id.etEmailReg);
        passwordET = findViewById(R.id.etPasswordReg);
        regBtn = findViewById(R.id.btnRegistration);
        contractEt = findViewById(R.id.etPhoneNumber);
        nameEt = findViewById(R.id.etNameReg);
        tvLogin = findViewById(R.id.tvLogin);


    }
     // FireStore put data method
    private void addInfo(String user_id, String user_name, String user_email, String user_phone_number, String user_current_location,String date_of_sub) {


        CollectionReference dbCourses = db.collection("Users");


        UserInfo userInfo = new UserInfo(user_id, user_name, user_email, user_phone_number, user_current_location,date_of_sub);


        dbCourses.add(userInfo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(RegisterActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}