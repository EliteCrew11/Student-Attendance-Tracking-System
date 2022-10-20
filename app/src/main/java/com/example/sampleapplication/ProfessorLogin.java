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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
        mAuth = FirebaseAuth.getInstance();


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfessorLogin.this, ResetActivity.class);
                intent.putExtra("REGISTER","PROFESSOR");
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    progressDialog=new ProgressDialog(ProfessorLogin.this);
                    progressDialog.setMessage("Loading....");


                    String emailUsername = enter_username.getText().toString().trim();
                    String passwordText = enter_paswword.getText().toString();


                    if (TextUtils.isEmpty(emailUsername)) {
                        Toast.makeText(ProfessorLogin.this, "Enter Mail", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(passwordText)) {
                        Toast.makeText(ProfessorLogin.this, "Enter password", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        progressDialog.show();

                        mAuth.signInWithEmailAndPassword(emailUsername, passwordText)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressDialog.cancel();
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(ProfessorLogin.this, InstructorEnrolledActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(ProfessorLogin.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });





    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfessorLogin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
