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

        
}


