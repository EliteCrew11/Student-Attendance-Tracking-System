package com.example.sampleapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;


public class StudentLogin extends AppCompatActivity {


    TextView resetPassword, createaccount;
    ImageView backButton, showpassword;
    EditText enter_paswword, _enter_username;
    ConstraintLayout login;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        resetPassword = findViewById(R.id.id_resetpassword);
        createaccount = findViewById(R.id.id_createaccount);
        enter_paswword = findViewById(R.id._id_enter_paswword);
        showpassword = findViewById(R.id.id_showpassword);
        _enter_username = findViewById(R.id._id_enter_username);
        backButton = findViewById(R.id.id_back);
        login = findViewById(R.id.id_login);
        mAuth = FirebaseAuth.getInstance();


        