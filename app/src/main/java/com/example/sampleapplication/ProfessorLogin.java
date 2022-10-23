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


public class ProfessorLogin extends AppCompatActivity {

    TextView resetPassword, createaccount;
    ImageView backButton, showpassword;
    EditText enter_paswword, enter_username;
    ConstraintLayout login;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_login);

        resetPassword = findViewById(R.id.id_resetpassword);
        login = findViewById(R.id.id_login);
        createaccount = findViewById(R.id.id_createaccount);
        showpassword = findViewById(R.id.id_showpassword);
        enter_username = findViewById(R.id._id_enter_username);
        enter_paswword = findViewById(R.id._id_enter_paswword);
        backButton = findViewById(R.id.id_back);


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfessorLogin.this, ResetActivity.class);
                intent.putExtra("RESET", "PROFESSOR");
                startActivity(intent);
            }
        });


        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfessorLogin.this, InstructorRegisterActivity.class);

                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfessorLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showpassword.getTag().equals("close")) {
                    showpassword.setTag("open");
                    enter_paswword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showpassword.setImageDrawable(getResources().getDrawable(R.drawable.eye_open_password));

                } else {
                    showpassword.setTag("close");
                    enter_paswword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showpassword.setImageDrawable(getResources().getDrawable(R.drawable.eye_close_password));

                }
            }
        });

        