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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        instructorCourseAdapter = new InstructorCourseAdapter(this, course);
        instructor_recylerlview.setAdapter(instructorCourseAdapter);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(InstructorEnrolledActivity.this,ProfessorLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getStudentCourses(ArrayList<String> course) {
        progressDialog = new ProgressDialog(InstructorEnrolledActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Instructor");
        String studentID = FirebaseAuth.getInstance().getUid();
        myRef.child(studentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                Log.d("Instructor Course", snapshot.toString());
                RegistrationModel post = snapshot.getValue(RegistrationModel.class);
                course.clear();
                String courseData = post.getCourse();
                if(TextUtils.isEmpty(courseData)){

                }else{
                    if (!courseData.isEmpty()) {
                        String arr[] = courseData.split(",");
                        for (String a : arr) {
                            System.out.println(a);
                            course.add(a);
                        }
                        instructorCourseAdapter.notifyDataSetChanged();

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(InstructorEnrolledActivity.this, "Data Failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updatePassword() {
        String password = getIntent().getStringExtra("PASSWORD");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Instructor");
        String studentID = FirebaseAuth.getInstance().getUid();
        try {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("pass", password);
            myRef.child(studentID).updateChildren(hashMap);
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {

    }
}
