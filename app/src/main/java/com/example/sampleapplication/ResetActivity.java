package com.example.sampleapplication;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class ResetActivity extends AppCompatActivity {


    ImageView backButton, showconfirmpassword, showpassword;
    TextView text_facultyID;
    EditText enterNewPasswordFaculty, enterconfirmPasswordFaculty, id_enterFaculty;
    ConstraintLayout id_reset;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        backButton = findViewById(R.id.id_back);
        showconfirmpassword = findViewById(R.id.id_showconfirmpassword);
        showpassword = findViewById(R.id.id_showpassword);
        id_reset = findViewById(R.id.id_reset);
        id_enterFaculty = findViewById(R.id.id_enterFaculty);
        text_facultyID = findViewById(R.id.text_facultyID);
        enterconfirmPasswordFaculty = findViewById(R.id.id_enterConfirmPasswordFaculty);
        enterNewPasswordFaculty = findViewById(R.id.id_enterNewPasswordFaculty);

        String isFrom = getIntent().getStringExtra("RESET");

        if (isFrom.equals("PROFESSOR")) {
            text_facultyID.setText("Faculty ID (@nwmissouri.edu)*");
        } else {
            text_facultyID.setText("Sid (@nwmissouri.edu)*");
        }

        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showpassword.getTag().equals("close")) {
                    showpassword.setTag("open");
                    enterNewPasswordFaculty.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showpassword.setImageDrawable(getResources().getDrawable(R.drawable.eye_open_password));

                } else {
                    showpassword.setTag("close");
                    enterNewPasswordFaculty.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showpassword.setImageDrawable(getResources().getDrawable(R.drawable.eye_close_password));

                }
            }
        });


        showconfirmpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showconfirmpassword.getTag().equals("close")) {
                    showconfirmpassword.setTag("open");
                    enterconfirmPasswordFaculty.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showconfirmpassword.setImageDrawable(getResources().getDrawable(R.drawable.eye_open_password));

                } else {
                    showconfirmpassword.setTag("close");
                    enterconfirmPasswordFaculty.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showconfirmpassword.setImageDrawable(getResources().getDrawable(R.drawable.eye_close_password));

                }
            }
        });

        id_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(CommonUtils.isConnectedToInternet(ResetActivity.this)){
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        String email="";
                        email = id_enterFaculty.getText().toString();
                        if (email.isEmpty()){
                            Toast.makeText(ResetActivity.this, "Enter Mail ID", Toast.LENGTH_SHORT).show();
                        }else{
                            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetActivity.this, "Please check mail....", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ResetActivity.this, "Please try again after sometime....", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }else{
                        Toast.makeText(ResetActivity.this, "No Internet Connection..", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
