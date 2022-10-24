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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

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


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentLogin.this, ResetActivity.class);
                intent.putExtra("RESET", "STUDENT");
                startActivity(intent);
            }
        });

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentLogin.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentLogin.this, MainActivity.class);
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


                    progressDialog = new ProgressDialog(StudentLogin.this);
                    progressDialog.setMessage("Loading....");


                    String emailUsername = _enter_username.getText().toString();
                    String passwordText = enter_paswword.getText().toString();



                    if (TextUtils.isEmpty(emailUsername)) {
                        Toast.makeText(StudentLogin.this, "Enter Mail", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(passwordText)) {
                        Toast.makeText(StudentLogin.this, "Enter password", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        progressDialog.show();


                        if (CommonUtils.isConnectedToInternet(StudentLogin.this)) {

                            mAuth.signInWithEmailAndPassword(emailUsername, passwordText)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressDialog.cancel();

                                            if (task.isSuccessful()) {
                                                verifyUserType(passwordText);
                                            } else {
                                                task.addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(StudentLogin.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        }
                                    });
                        } else {
                            progressDialog.cancel();
                            Toast.makeText(StudentLogin.this, "No Internet Connection..", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void verifyUserType(String passwordText) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Student");
        String studentID = FirebaseAuth.getInstance().getUid();
        myRef.child(studentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userType = snapshot.child("type").getValue(String.class);
                if (userType != null && userType.equals("student")) {
                    Intent intent = new Intent(StudentLogin.this, StudentEnrolledCoursesActivity.class);
                    intent.putExtra("PASSWORD", passwordText.toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(StudentLogin.this, "Enter with Student Credentials",
                            Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StudentLogin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
