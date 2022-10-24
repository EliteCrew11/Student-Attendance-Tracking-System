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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {


    TextView text_studentid;
    ImageView back;
    EditText enterFirstName, enterLastName, enterStudentID, enterPassword, enterConfirmPassword;
    ConstraintLayout register;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    MaterialCheckBox javaCourse, pmCourse, gdpCourse, bigdataCourse;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

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

                    progressDialog = new ProgressDialog(RegistrationActivity.this);
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
                        Toast.makeText(RegistrationActivity.this, "Enter FirstName", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(lname)) {
                        Toast.makeText(RegistrationActivity.this, "Enter LastName", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(sID)) {
                        Toast.makeText(RegistrationActivity.this, "Enter id", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(pass)) {
                        Toast.makeText(RegistrationActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(confirmpass)) {
                        Toast.makeText(RegistrationActivity.this, "Enter confirm password", Toast.LENGTH_LONG).show();
                    } else if (!(pass.equals(confirmpass))) {
                        Toast.makeText(RegistrationActivity.this, "password mismtach", Toast.LENGTH_LONG).show();
                    } else if (course.equals("")) {
                        Toast.makeText(RegistrationActivity.this, "select course ", Toast.LENGTH_LONG).show();
                    } else {
                        String list = course;
                        progressDialog.show();

                        if(CommonUtils.isConnectedToInternet(RegistrationActivity.this)){

                            auth = FirebaseAuth.getInstance();
                            auth.createUserWithEmailAndPassword(sID, pass)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressDialog.cancel();
                                            if (task.isSuccessful()) {
                                                getEmailVerification(auth, sID);

                                                Toast.makeText(RegistrationActivity.this, "Registration Successfull", Toast.LENGTH_LONG).show();

                                                try {
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference myRef = database.getReference("UserDetails").child("Student");
                                                    String studentID = FirebaseAuth.getInstance().getUid();
                                                    RegistrationModel registrationModel = new RegistrationModel(fname, lname, sID, pass, list,"student");
                                                    myRef.child(studentID).setValue(registrationModel);

                                                } catch (Exception e) {

                                                }

                                                Intent intent = new Intent(RegistrationActivity.this, StudentLogin.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                           task.addOnFailureListener(new OnFailureListener() {
                                               @Override
                                               public void onFailure(@NonNull Exception e) {
                                                      Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                               }
                                           });
                                           //     Toast.makeText(RegistrationActivity.this, "Registration UnSuccessfull", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(RegistrationActivity.this, "No Internet Connection..", Toast.LENGTH_LONG).show();
                        }


                    }

                } catch (Exception exception) {
                    Toast.makeText(RegistrationActivity.this, "Try again after sometime", Toast.LENGTH_LONG).show();
                }


            }
        });

    }



    private void getEmailVerification(FirebaseAuth auth, String sID) {
        FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "check email for verification", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
