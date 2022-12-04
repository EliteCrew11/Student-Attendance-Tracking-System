package com.example.sampleapplication.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleapplication.MainActivity;
import com.example.sampleapplication.R;
import com.example.sampleapplication.student.models.StudentPieModel;
import com.example.sampleapplication.student.models.StudentRegistrationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentProfileUpdate extends AppCompatActivity {
    TextInputEditText enterName, enterLastName;
    TextView enterCourse, textView, unselected;
    Button submit_update;
    ImageView id_back;
    MaterialCheckBox javaCourse, pmCourse, gdpCourse, bigdataCourse;

    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Java (13204)", "Project Management (23123)","GDP (13896)", "Big Data (13900)"};

    ArrayList<Integer> UnlangList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_update);
        UnlangList.add(0);
        UnlangList.add(1);
        UnlangList.add(2);
        UnlangList.add(3);
        enterCourse = findViewById(R.id.enterCourse);
        enterName = findViewById(R.id.enterFirstName);
        unselected = findViewById(R.id.unselected);
        enterLastName = findViewById(R.id.enterLastName);
        submit_update = findViewById(R.id.submit_update);
        bigdataCourse = findViewById(R.id.bigdataCourse);
        gdpCourse = findViewById(R.id.gdpCourse);
        textView = findViewById(R.id.textView);
        pmCourse = findViewById(R.id.pmCourse);
        javaCourse = findViewById(R.id.javaCourse);
        id_back = findViewById(R.id.id_back);

        selectedLanguage = new boolean[langArray.length];


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentProfileUpdate.this);

                // set title
                builder.setTitle("Select Courses");

                // set dialog non cancelable
                builder.setCancelable(false);


                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);

                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(",");
                            }
                        }

                        // set text on textView
                        textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });


        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        String firstname = getIntent().getStringExtra("FIRSTNAME");
        String lastname = getIntent().getStringExtra("LASTNAME");
        String sid = getIntent().getStringExtra("SID");
        String pass = getIntent().getStringExtra("PASS");
        String course = getIntent().getStringExtra("COURSE");
        String type = getIntent().getStringExtra("TYPE");
        String key = getIntent().getStringExtra("KEY");
        StudentRegistrationModel registrationModel = new StudentRegistrationModel(firstname, lastname, key, sid, pass, course, "student");


        enterCourse.setText(course);
        enterName.setText(firstname);
        enterLastName.setText(lastname);

        submit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = enterName.getText().toString().trim();
                String lastname = enterLastName.getText().toString().trim();
                String enteredcourse = enterCourse.getText().toString().trim();
                String newcourse = textView.getText().toString();


                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(StudentProfileUpdate.this, "Enter FirstName", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(StudentProfileUpdate.this, "Enter LastName", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(enteredcourse)) {
                    Toast.makeText(StudentProfileUpdate.this, "Enter Course", Toast.LENGTH_SHORT).show();
                } else {
                    updateName(name, key, lastname, course, newcourse, registrationModel);
                    //       checkCourse(course,enteredcourse,key);


                }
            }
        });
    }

    private void checkCourse(String course, String enteredcourse, String key) {
     /*   String arr[] = course.split(",");
        ArrayList<String> courseone = new ArrayList<>();
        for (String a : arr) {
            System.out.println(a);
            courseone.add(a);
        }*/
        String arr1[] = enteredcourse.split(",");
        ArrayList<String> coursetwo = new ArrayList<>();
        for (String a : arr1) {
            System.out.println(a);
            coursetwo.add(a);
        }

        // update in student profile
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Student");
        myRef.child(key).child("course").setValue(enteredcourse);

        // update in student attendence

        DatabaseReference myRefss = database.getReference("StudentAttendence");
        for (int i = 0; i < coursetwo.size(); i++) {
            StudentPieModel studentPieModel = new StudentPieModel("0", "0", "0");
            myRefss.child(coursetwo.get(i)).child(key).setValue(studentPieModel);
        }

        // update in faculty attendence
        DatabaseReference myRefssss = database.getReference("facultyAttendence");

        for (int i = 0; i < coursetwo.size(); i++) {
            StudentPieModel studentPieModel = new StudentPieModel("0", "0", "0");
            //    myRefssss.child(coursetwo.get(i)).child(facultyIS).child("0000").setValue(studentPieModel);
        }


    }

    private void updateName(String name, String key, String lastname, String course, String newcourse, StudentRegistrationModel registrationModel) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Student");

        if (name.length() > 0) {
            myRef.child(key).child("fname").setValue(name);
        }

        if (lastname.length() > 0) {
            myRef.child(key).child("lname").setValue(lastname).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!(course.equalsIgnoreCase(newcourse))) {
                        if (newcourse.isEmpty()) {
                            Toast.makeText(StudentProfileUpdate.this, "Data Updated....", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StudentProfileUpdate.this, AdminDashboard.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            UpdateData(course, newcourse, key, registrationModel);
                        }
                    } else {
                        Toast.makeText(StudentProfileUpdate.this, "Data Updated....", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudentProfileUpdate.this, AdminDashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

    }

    private void UpdateData(String oldcourse, String newcourse, String key, StudentRegistrationModel registrationModel) {
        String oldArray[] = oldcourse.split(",");
        String[] newArray = newcourse.split(",");

        Set<String> set = new HashSet<>();
        Set<String> notcommon = new HashSet<>();


        ArrayList<String> notInList = new ArrayList<>();
        notInList.add("Java (13800)");
        notInList.add("Project Management (13800)");
        notInList.add("GDP-1 (13800)");
        notInList.add("Big Data (13800)");
        set.addAll(notInList);
        notcommon.addAll(Arrays.asList(newArray));

        for(int i=0;i< newArray.length;i++){
            set.remove(newArray[i]);
        }
        ArrayList<String> removeList
                = new ArrayList<>(set);
        String[] addesList=newArray;

        getLatestCourse(newcourse,key,registrationModel,removeList,addesList);



        //   UpdateCourses(newcourse, key, registrationModel, newArray, notcommon, notInList);

    }

    private void getLatestCourse(String newcourse, String key, StudentRegistrationModel registrationModel, ArrayList<String> removeList, String[] addesList) {

        try {
            registrationModel.setCourseList(newcourse);
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            // Updating courses in student list data
            DatabaseReference userDatabaseReference = database.getReference("UserDetails").child("Student");
            userDatabaseReference.child(key).setValue(registrationModel);

            // updating and deleting courses from student attendence
            DatabaseReference studentAttendenceListReference = database.getReference("Attendence").child("studentAttendenceList");
            studentAttendenceListReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChildren()){

                        // deleting records
                        for(int i=0;i<removeList.size();i++){
                            if(snapshot.child(removeList.get(i)).hasChild(key)){
                                studentAttendenceListReference.child(removeList.get(i)).child(key).removeValue();
                            }else{

                            }

                        }

                        // adding records
                        for (int j=0;j<addesList.length;j++){
                            if(snapshot.child(addesList[j]).hasChild(key)){

                            }else{
                                StudentPieModel studentPieModel = new StudentPieModel("0", "0", "0");
                                studentAttendenceListReference.child(addesList[j]).child(key).setValue(studentPieModel);
                            }
                        }


                    }
                    Toast.makeText(StudentProfileUpdate.this, "Data Updated....", Toast.LENGTH_SHORT).show();
                  //  finish();
                    Intent intent = new Intent(StudentProfileUpdate.this, AdminDashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void UpdateCourses(String newcourse, String key, StudentRegistrationModel registrationModel, String[] newArray, Set<String> notcommon, ArrayList<String> notInList) {


        registrationModel.setCourseList(newcourse);
        // update course list in student Table
        try {
/*

            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> nolist = new ArrayList<>();
            for (int i = 0; i < newArray.length; i++) {
                list.add(newArray[i]);
            }


            notInList.retainAll(Arrays.asList(list)); // Same elements retained


            List<String> list1 = list;
            List<String> list2 = notInList;
// Prepare a union
            List<String> union = new ArrayList<String>(list1);
            union.addAll(list2);
// Prepare an intersection
            List<String> intersection = new ArrayList<String>(list1);
            intersection.retainAll(list2);
// Subtract the intersection from the union
            union.removeAll(intersection);
// Print the result
            for (String n : union) {
                System.out.println(n);
            }
*/

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            // save student details in firebase database

            DatabaseReference userDatabaseReference = database.getReference("UserDetails").child("Student");
            userDatabaseReference.child(key).setValue(registrationModel);

            DatabaseReference studentAttendenceListReference = database.getReference("Attendence").child("studentAttendenceList");
            studentAttendenceListReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            /*
            studentAttendenceListReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (int i = 0; i <= list.size(); i++) {
                        if (snapshot.hasChild(list.get(i))) {
                            // check whether student Key exists in perticular course or not
                            if (snapshot.child(list.get(i)).hasChild(key)) {
                                // student key is there in this course

                            } else {
                                // student key is not there in this course
                                StudentPieModel studentPieModel = new StudentPieModel("0", "0", "0");
                                studentAttendenceListReference.child(list.get(i)).child(key).setValue(studentPieModel);
                            }
                        }
                    }

                    try {
                        // deleting record
                        for (int i = 0; i <= union.size(); i++) {
                            if (snapshot.hasChild(union.get(i))) {
                                // check whether student Key exists in perticular course or not
                                if (snapshot.child(list.get(i)).hasChild(key)) {
                                    // student key is there in this course
                                    studentAttendenceListReference.child(union.get(i)).child(key).removeValue();

                                } else {
                                    // student key is not there in this course
                                    //        StudentPieModel studentPieModel = new StudentPieModel("0", "0", "0");
                                    //      studentAttendenceListReference.child(list.get(i)).child(key).setValue(studentPieModel);
                                }
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(StudentProfileUpdate.this, "Data Updated....", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
      */     /* for (int i = 0; i < list.size(); i++) {
                StudentPieModel studentPieModel = new StudentPieModel("0", "0", "0");
                studentAttendenceListReference.child(list.get(i)).child(key).setValue(studentPieModel);
            }
*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
