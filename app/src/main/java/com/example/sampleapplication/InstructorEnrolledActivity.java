package com.example.sampleapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleapplication.professor.InstructorCourseAdapter;
import com.example.sampleapplication.student.StudentCourseAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructorEnrolledActivity extends AppCompatActivity {


    ImageView logout;
    RecyclerView instructor_recylerlview;
    private DatabaseReference mDatabase;
    InstructorCourseAdapter instructorCourseAdapter;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_enrolled);

        instructor_recylerlview = findViewById(R.id.student_recylerlview);
        instructor_recylerlview.setLayoutManager(new LinearLayoutManager(this));
        logout=findViewById(R.id.id_logout);

        ArrayList<String> course = new ArrayList<>();
        if (CommonUtils.isConnectedToInternet(InstructorEnrolledActivity.this)) {
            try {
                updatePassword();
                getStudentCourses(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(InstructorEnrolledActivity.this, "No Internet Connection....", Toast.LENGTH_SHORT).show();
        }
        }
