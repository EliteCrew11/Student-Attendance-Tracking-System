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

}


