package com.nahidd.dholaimama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailET,passwordET;
    private Button loginBtn;
    private TextView registrationNow, forgotPassword;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.etEmailLogin);
        passwordET = findViewById(R.id.etPasswordLogin);
        loginBtn = findViewById(R.id.btnLogin);
        registrationNow = findViewById(R.id.tvRegisterNow);
        forgotPassword = findViewById(R.id.tvForgotPass);

        loginBtn.setOnClickListener(this);
        registrationNow.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {

        int  id = view.getId();
        if(id==R.id.btnLogin){

            loginUserAccount();
        }
        if(id==R.id.tvRegisterNow){
            Intent registerNowIntent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(registerNowIntent);
        }
        if(id==R.id.tvForgotPass){
            Intent forgotPassIntent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
            startActivity(forgotPassIntent);
        }

    }

    private void loginUserAccount() {


        String email, password;
        email = emailET.getText().toString();
        password = passwordET.getText().toString();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

}