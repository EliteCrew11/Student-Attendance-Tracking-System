package com.example.sampleapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.sampleapplication.student.StudentCourseAdapter;
import com.example.sampleapplication.student.models.StudentPieModel;
import com.example.sampleapplication.student.models.StudentRegistrationModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentEnrolledCoursesActivity extends AppCompatActivity {

    ImageView logout;
    RecyclerView student_recylerlview;
    private DatabaseReference mDatabase;
    StudentCourseAdapter studentCourseAdapter;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enrolled_courses);
        logout = findViewById(R.id.id_logout);
        student_recylerlview = findViewById(R.id.student_recylerlview);
        student_recylerlview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> course = new ArrayList<>();
        if (CommonUtils.isConnectedToInternet(StudentEnrolledCoursesActivity.this)) {
            try {
                updatePassword();
                getStudentCourses(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(StudentEnrolledCoursesActivity.this, "No Internet Connection....", Toast.LENGTH_SHORT).show();
        }
        studentCourseAdapter = new StudentCourseAdapter(this, course);
        student_recylerlview.setAdapter(studentCourseAdapter);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  checkStudent();
           //     saveattendence();
                               FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(StudentEnrolledCoursesActivity.this, StudentLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private void checkStudent() {

        FirebaseDatabase facStuDetails = FirebaseDatabase.getInstance();
        final String[] value = {"NO"};
        String studentID=FirebaseAuth.getInstance().getUid();
        final Boolean[] present = {false};
        DatabaseReference facsturefDatabase = facStuDetails.getReference("facStuDetails").child("GDP-1 (13800)");
        facsturefDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("23-Oct-2022")){
                    for (DataSnapshot sddd: snapshot.getChildren()) {
                        if(sddd.hasChild(studentID)){
                            present[0] =true;
                            value[0] ="yes";
                        }
                    }

                    if(value[0].equals("yes")){
                        Toast.makeText(StudentEnrolledCoursesActivity.this,"Update Attendence",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(StudentEnrolledCoursesActivity.this,"Update Not Attendence",Toast.LENGTH_LONG).show();
                    }

                }else{
                    facsturefDatabase.child("23-Oct-2022").child(studentID).setValue("present").addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentEnrolledCoursesActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void saveattendence() {
        FirebaseDatabase teachDatabase = FirebaseDatabase.getInstance();
        String studentID = FirebaseAuth.getInstance().getUid();
        DatabaseReference teachrefDatabase = teachDatabase.getReference("facultyAttendence").child("GDP-1 (13800),").child("Zv91VIto9cVVz4VNt5JBi8LoQis2");
        String couser="GDP-1 (13800)";
        teachrefDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot postSnapshot) {

                for (DataSnapshot sddd: postSnapshot.getChildren()) {
                    StudentPieModel studentPieModesl=sddd.getValue(StudentPieModel.class);

                    for (DataSnapshot abcd: sddd.getChildren()) {
                            StudentPieModel studentPieMod=abcd.getValue(StudentPieModel.class);

                            for (DataSnapshot efgh: abcd.getChildren()) {
                                StudentPieModel studentPieMode=efgh.getValue(StudentPieModel.class);;

                            }
                        }
                        if(studentID.equals(sddd.getKey())){
                            StudentPieModel studentPieModel = sddd.getValue(StudentPieModel.class);
                            String present = studentPieModel.getPresentDays();
                            String absent = studentPieModel.getAbsentDays();
                            String late = studentPieModel.getLateDays();
                            int presentDay = Integer.parseInt(present);
                            presentDay = presentDay + 1;
                            String pres = Integer.toString(presentDay);
                            DatabaseReference myRefss = teachDatabase.getReference("facultyAttendence");
                            String studentID = FirebaseAuth.getInstance().getUid();

                            StudentPieModel studentPieModels = new StudentPieModel(pres, absent, late);
                            //myRefss.child(courseName).child(studentID).setValue(studentPieModels);


                        }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updatePassword() {
        String password = getIntent().getStringExtra("PASSWORD");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Student");
        String studentID = FirebaseAuth.getInstance().getUid();
        try {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("password", password);
            myRef.child(studentID).updateChildren(hashMap);
        } catch (Exception e) {

        }
    }

    private void getStudentCourses(ArrayList<String> course) {
        progressDialog = new ProgressDialog(StudentEnrolledCoursesActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Student");
        String studentID = FirebaseAuth.getInstance().getUid();
        myRef.child(studentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                Log.d("Student Course", snapshot.toString());
                StudentRegistrationModel post = snapshot.getValue(StudentRegistrationModel.class);
                course.clear();
                String courseData = post.getCourseList();
                if(TextUtils.isEmpty(courseData)){

                }else{
                    SharedPreferences sharedpreferences =getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("STUDENTNAME",post.getLname().toString());
                    editor.commit();
                    if (!courseData.isEmpty()) {
                        String arr[] = courseData.split(",");
                        for (String a : arr) {
                            System.out.println(a);
                            course.add(a);
                        }
                        studentCourseAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(StudentEnrolledCoursesActivity.this, "Data Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
