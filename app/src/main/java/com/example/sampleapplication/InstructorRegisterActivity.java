package com.example.sampleapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class InstructorRegisterActivity extends AppCompatActivity {


    TextView spinner_btn, text_studentid;
    ImageView back;
    EditText enterFirstName, enterLastName, enterStudentID, enterPassword, enterConfirmPassword;
    ConstraintLayout register;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    MaterialCheckBox javaCourse, pmCourse, gdpCourse, bigdataCourse;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_registration);

        text_studentid = findViewById(R.id.text_studentid);
        back = findViewById(R.id.id_back);
        register = findViewById(R.id.id_login);
        enterFirstName = findViewById(R.id.id_enterFirstName);
        enterLastName = findViewById(R.id.id_enterLastName);
        enterStudentID = findViewById(R.id.id_enterStudentID);
        enterPassword = findViewById(R.id.id_enterPassword);
        enterConfirmPassword = findViewById(R.id.id_enterconfirmPassword);
        javaCourse = findViewById(R.id.javaCourse);
        pmCourse = findViewById(R.id.pmCourse);
        gdpCourse = findViewById(R.id.gdpCourse);
        bigdataCourse = findViewById(R.id.bigdataCourse);

        auth = FirebaseAuth.getInstance();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    progressDialog = new ProgressDialog(InstructorRegisterActivity.this);
                    progressDialog.setMessage("Loading....");
                    String fname = enterFirstName.getText().toString();
                    String lname = enterLastName.getText().toString();
                    String sID = enterStudentID.getText().toString();
                    String pass = enterPassword.getText().toString();
                    String confirmpass = enterConfirmPassword.getText().toString();
                    String java = "";
                    String pm = "";
                    String gdp = "";
                    String bigdata = "";
                    String course = "";

                    if (javaCourse.isChecked()) {
                        java = "Java (13800),";
                        course = java;
                    }
                    if (pmCourse.isChecked()) {
                        pm = "Project Management (13800),";
                        course = course + pm;
                    }
                    if (gdpCourse.isChecked()) {
                        gdp = "GDP-1 (13800),";
                        course = course + gdp;
                    }
                    if (bigdataCourse.isChecked()) {
                        bigdata = "Big Data (13800),";
                        course = course + bigdata;
                    }


                    if (TextUtils.isEmpty(fname)) {
                        Toast.makeText(InstructorRegisterActivity.this, "Enter FirstName", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(lname)) {
                        Toast.makeText(InstructorRegisterActivity.this, "Enter LastName", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(sID)) {
                        Toast.makeText(InstructorRegisterActivity.this, "Enter id", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(pass)) {
                        Toast.makeText(InstructorRegisterActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(confirmpass)) {
                        Toast.makeText(InstructorRegisterActivity.this, "Enter confirm password", Toast.LENGTH_LONG).show();
                    } else if (!(pass.equals(confirmpass))) {
                        Toast.makeText(InstructorRegisterActivity.this, "password mismtach", Toast.LENGTH_LONG).show();
                    }else if (course.equals("")) {
                        Toast.makeText(InstructorRegisterActivity.this, "select course ", Toast.LENGTH_LONG).show();
                    }  else {
                        String list = course;

                        progressDialog.show();
                        if (CommonUtils.isConnectedToInternet(InstructorRegisterActivity.this)) {
                            auth.createUserWithEmailAndPassword(sID, confirmpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.cancel();
                                    if (task.isSuccessful()) {
                                        //            Toast.makeText(RegistrationActivity.this,"Registration Successfull",Toast.LENGTH_LONG).show()
                                        Toast.makeText(InstructorRegisterActivity.this, "Registration Successfull", Toast.LENGTH_LONG).show();

                                        try {
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef = database.getReference("UserDetails").child("Instructor");
                                            String studentID = FirebaseAuth.getInstance().getUid();

                                            RegistrationModel registrationModel = new RegistrationModel(fname, lname, sID, pass, list,"instructor");
                                            myRef.child(studentID).setValue(registrationModel);

                                        } catch (Exception e) {

                                        }

                                        Intent intent = new Intent(InstructorRegisterActivity.this, ProfessorLogin.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        task.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(InstructorRegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(InstructorRegisterActivity.this, "No Internet Connection..", Toast.LENGTH_SHORT).show();
                        }
                        }

                } catch (Exception exception) {
                    Toast.makeText(InstructorRegisterActivity.this, "Try again after sometime", Toast.LENGTH_LONG).show();
                }


            }
        });

    }


}

