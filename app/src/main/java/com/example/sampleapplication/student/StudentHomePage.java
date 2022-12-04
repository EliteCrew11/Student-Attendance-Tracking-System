package com.example.sampleapplication.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.StudentEnrolledCoursesActivity;
import com.example.sampleapplication.StudentModel;
import com.example.sampleapplication.databinding.ActivityStudentHomePageBinding;
import com.example.sampleapplication.student.models.StudentPieModel;
import com.example.sampleapplication.student.models.StudentRegistrationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class StudentHomePage extends AppCompatActivity {

    AppBarConfiguration appBarConfiguration;
    ActivityStudentHomePageBinding binding;
    Toolbar toolbar;
    TextView studentName;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String course = getIntent().getStringExtra("CourseType");
        String coursecrn="";
        if(course.contains("Java")){
            coursecrn="Java (13204)";
        } else  if(course.contains("Project")){
            coursecrn="Project Management (23123)";
        }
        else  if(course.contains("GDP")){
            coursecrn="GDP (13896)";
        } else  if(course.contains("Big")){
            coursecrn="Big Data (13900)";
        } else {
            coursecrn=course;
        }

        getSupportActionBar().setTitle(coursecrn);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        TextView name = navView.getHeaderView(0).findViewById(R.id.studentName);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String subject = sharedpreferences.getString("STUDENTNAME", "");


        name.setText(subject);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student_home_page);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_profile)
                .setOpenableLayout(drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student_home_page);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                if (intentResult.getContents() == null) {

                } else {
                    markingAttendence(intentResult.getContents().toString());


                    //   makeAttendence(intentResult.getContents().toString());
                }
            } else {

            }
        }
    }

    private void markingAttendence(String data) {

        // retreiving data from QRCODE
        String arr[] = data.split(",");
        ArrayList<String> facultyData = new ArrayList<>();
        for (String a : arr) {
            System.out.println(a);
            facultyData.add(a);
        }
        if (facultyData.size() > 0) {
            String teacherUniqueID = facultyData.get(0).toString();
            String subject = facultyData.get(1).toString();
            String date = facultyData.get(2).toString();
            String presLate = facultyData.get(3).toString();
            String studentIsD = FirebaseAuth.getInstance().getUid();

            // Retreive Attendence sheet mark

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("MarkAttendence").child(teacherUniqueID).child(subject)
                    .child(date);

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String totalPresent = snapshot.child("StudentCount").child("present").getValue(String.class);
                    String totalAbsent = snapshot.child("StudentCount").child("absent").getValue(String.class);
                    String totalLate = snapshot.child("StudentCount").child("late").getValue(String.class);
                    String date = snapshot.getKey();
                    Boolean isStudentPresent = snapshot.child("present").hasChild(studentIsD);
                    Boolean isStudentLate = snapshot.child("").hasChild(studentIsD);
                    Boolean idInAbsent = snapshot.child("absent").hasChild(studentIsD);
                    if (idInAbsent) {

                        if (!(totalAbsent.equals("0"))) {
                            int absent = Integer.parseInt(totalAbsent) - 1;
                            totalAbsent = String.valueOf(absent);
                            myRef.child("StudentCount").child("absent").setValue(totalAbsent);

                        }

                        if (presLate.equalsIgnoreCase("present")) {
                            int present = Integer.parseInt(totalPresent) + 1;
                            totalPresent = String.valueOf(present);
                            myRef.child("StudentCount").child("present").setValue(totalPresent);

                        } else {
                            int present = Integer.parseInt(totalLate) + 1;
                            totalLate = String.valueOf(present);
                            myRef.child("StudentCount").child("late").setValue(totalLate);


                        }


                        StudentRegistrationModel model = null;


                        if (idInAbsent) {
                            model = snapshot.child("absent").child(studentIsD).getValue(StudentRegistrationModel.class);
                            myRef.child("absent").child(studentIsD).removeValue();
                        }
                        if (model != null) {
                            if (presLate.equalsIgnoreCase("present")) {
                                myRef.child("present").child(studentIsD).setValue(model);
                            } else {
                                myRef.child("late").child(studentIsD).setValue(model);
                            }

                        }

                        // set attendence for student
                        DatabaseReference studentAttendenceListReference = database.getReference("Attendence").child("studentAttendenceList")
                                .child(subject).child(studentIsD);
                        studentAttendenceListReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                StudentPieModel studentPieModel = snapshot.getValue(StudentPieModel.class);
                                String present = studentPieModel.getPresentDays();
                                String late = studentPieModel.getLateDays();
                                String absent = studentPieModel.getAbsentDays();

                                if (presLate.equalsIgnoreCase("present")) {
                                    int pre = Integer.parseInt(present) + 1;
                                    studentAttendenceListReference.setValue(new StudentPieModel(String.valueOf(pre), absent, late));
                                } else {
                                    int pre = Integer.parseInt(late) + 1;
                                    studentAttendenceListReference.setValue(new StudentPieModel(present, absent, String.valueOf(pre)));
                                }

                                Toast.makeText(StudentHomePage.this, "Attendance has marked..", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    } else {
                        Toast.makeText(StudentHomePage.this, "You have already scan the QR and attendance was marked..", Toast.LENGTH_SHORT).show();
                    }

                    if (progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.cancel();

                }
            });


        }

    }

    private void makeAttendence(String data) {
        String arr[] = data.split(",");
        ArrayList<String> facultyData = new ArrayList<>();
        for (String a : arr) {
            System.out.println(a);
            facultyData.add(a);
        }
        if (facultyData.size() > 0) {
            String teacherID = facultyData.get(0).toString();
            String nameID = teacherID.replace("@", "_");
            String facultyIS = nameID.replace(".", "_");
            String courseName = facultyData.get(1).toString();
            String date = facultyData.get(2).toString();
            String studentIsD = FirebaseAuth.getInstance().getUid();

            // check if student is present or not



/*
            if(checkStudentPresentorNot(date,studentIsD)) {
              saveStudentAttendence(courseName);
              saveFaultyAttendence(date,facultyIS,courseName,studentIsD);
            }else{
                // No Student Is Present
            }

*/


            FirebaseDatabase facStuDetails = FirebaseDatabase.getInstance();
            final String[] value = {"NO"};
            String studentID = FirebaseAuth.getInstance().getUid();
            final Boolean[] present = {false};
            DatabaseReference facsturefDatabase = facStuDetails.getReference("facStuDetails").child(courseName);
            facsturefDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(date)) {
                        //   snapshot.child(date).
                        if (snapshot.child(date).child(studentID).exists()) {
                            present[0] = true;
                            value[0] = "yes";
                        } else {

                        }
                      /*  for (DataSnapshot sddd: snapshot.getChildren()) {
                            if(sddd.hasChild(studentID)){
                                present[0] =true;
                                value[0] ="yes";
                            }
                        }*/

                        if (value[0].equals("yes")) {
                            Toast.makeText(StudentHomePage.this, "Attendence has marked already..", Toast.LENGTH_LONG).show();
                        } else {
                            saveStudentAttendence(courseName);
                            saveFaultyAttendence(date, facultyIS, courseName, studentIsD);
                            facsturefDatabase.child(date).child(studentID).setValue("present");


                            Toast.makeText(StudentHomePage.this, "Attendence has been mark", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        facsturefDatabase.child(date).setValue("present").addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                makeAttendence(data);
                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });


        }


    }


    private void saveFaultyAttendence(String date, String facultyIS, String courseName, String studentID) {


        // Faculty Attendence
        FirebaseDatabase teachDatabase = FirebaseDatabase.getInstance();
        DatabaseReference teachrefDatabase = teachDatabase.getReference("facultyAttendence").child(courseName).child(facultyIS);
        String couser = "GDP-1 (13800)";
        teachrefDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot postSnapshot) {

                for (DataSnapshot sddd : postSnapshot.getChildren()) {
                    if (date.equals(sddd.getKey())) {
                        StudentPieModel studentPieModesl = sddd.getValue(StudentPieModel.class);
                        String present = studentPieModesl.getPresentDays();
                        String absent = studentPieModesl.getAbsentDays();
                        String late = studentPieModesl.getLateDays();
                        int presentDay = Integer.parseInt(present);
                        presentDay = presentDay + 1;
                        String pres = Integer.toString(presentDay);
                        StudentPieModel studentPieModels = new StudentPieModel(pres, absent, late);
                        teachrefDatabase.child(date).setValue(studentPieModels);


                    } else {
                        StudentPieModel studentPieModel = new StudentPieModel("1", "0", "0");
                        teachrefDatabase.child(date).setValue(studentPieModel);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveStudentAttendence(String courseName) {
        // Save Student Total Attendence
        FirebaseDatabase studentPieDatabase = FirebaseDatabase.getInstance();
        String studentID = FirebaseAuth.getInstance().getUid();
        DatabaseReference refPieDatabase = studentPieDatabase.getReference("StudentAttendence").child(courseName).child(studentID);

        refPieDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (studentID.equals(snapshot.getKey())) {
                    StudentPieModel studentPieModel = snapshot.getValue(StudentPieModel.class);
                    String present = studentPieModel.getPresentDays();
                    String absent = studentPieModel.getAbsentDays();
                    String late = studentPieModel.getLateDays();
                    int presentDay = Integer.parseInt(present);
                    presentDay = presentDay + 1;
                    String pres = Integer.toString(presentDay);
                    DatabaseReference myRefss = studentPieDatabase.getReference("StudentAttendence");
                    String studentID = FirebaseAuth.getInstance().getUid();

                    StudentPieModel studentPieModels = new StudentPieModel(pres, absent, late);
                    myRefss.child(courseName).child(studentID).setValue(studentPieModels);


                }
                //       registrationModelArrayList.add(post);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private boolean checkStudentPresentorNot(String date, String studentID) {
        FirebaseDatabase facStuDetails = FirebaseDatabase.getInstance();
        final Boolean[] present = {false};
        DatabaseReference facsturefDatabase = facStuDetails.getReference("facStuDetails").child(date);
        facsturefDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sddd : snapshot.getChildren()) {
                    if (studentID.equals(sddd.getKey())) {
                        present[0] = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return present[0];
    }
}
