package com.example.sampleapplication.professor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleapplication.CommonUtils;
import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.professor.ui.StudentProfileModelss;
import com.example.sampleapplication.student.models.StudentProfileModel;
import com.example.sampleapplication.student.models.StudentRegistrationModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentProfile extends AppCompatActivity {

    RecyclerView studentprofile_list;
    ArrayList<StudentProfileModel> arrayList;
    ArrayList<String> presentList;
    SearchView id_searchview;
    StudentProfileAdapter studentProfileAdapter;
    ArrayList<StudentProfileModelss> studentUserIDList = new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        studentprofile_list = findViewById(R.id.studentprofile_list);
        studentprofile_list.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(studentprofile_list.getContext(),
                new LinearLayoutManager(this).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.line_decoration));

        studentprofile_list.addItemDecoration(dividerItemDecoration);
        id_searchview=findViewById(R.id.id_searchview);
        // id_searchview.setQueryHint(Html.fromHtml("<font color = #000000>" + getResources().getString(R.string.search_hint) + "</font>"));
        int id = id_searchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) id_searchview.findViewById(id);
        textView.setTextColor(Color.BLACK);
        id_searchview.clearFocus();

        String course = getIntent().getStringExtra("COURSE");
        String date = getIntent().getStringExtra("DATE");
        String faculty = getIntent().getStringExtra("FACULTY");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("facStuDetails").child(course).child(date);


        id_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        if (CommonUtils.isConnectedToInternet(this)) {
            try {
                progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Loading....");
                progressDialog.show();
               // getPresentStudentList(myRef);
                arrayList=new ArrayList<>();
                getStudentList(arrayList,course);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(StudentProfile.this, "No Internet Connection....", Toast.LENGTH_SHORT).show();
        }


    }

    private void filterList(String newText) {

        ArrayList<StudentProfileModel> list=new ArrayList<>();
        for (StudentProfileModel model:arrayList){
            if(model.getName().toLowerCase().contains(newText.toLowerCase())){
                list.add(model);
            }
        }

        if(list.isEmpty()){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            studentProfileAdapter.setFilterList(list);
        }
    }

    private void getPresentStudentList(DatabaseReference myRef) {
        presentList=new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postsnapshot : snapshot.getChildren()){
                    presentList.add(postsnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.getMessage();
            }
        });
    }

    private void getStudentList(ArrayList<StudentProfileModel> arrayList, String course) {
        // Get  student attendence details

        String subject = getIntent().getStringExtra("subject");
        String date = getIntent().getStringExtra("DATE");
        String teacherUniqueID = getIntent().getStringExtra("FACULTY");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("MarkAttendence").child(teacherUniqueID).child(subject)
                .child(date);
        arrayList.clear();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean isStudentPresent=snapshot.child("present").hasChildren();
                Boolean isStudentAbsent=snapshot.child("absent").hasChildren();
                Boolean isStudentLate=snapshot.child("late").hasChildren();

                if(isStudentPresent){
                    myRef.child("present").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                StudentRegistrationModel model=snapshot1.getValue(StudentRegistrationModel.class);
                                arrayList.add(new StudentProfileModel(model.getFname(),"Yes","No",subject,model.getUniqueKey(),"No"));
                            }
                            if(arrayList.size()>0){
                                studentProfileAdapter = new StudentProfileAdapter(StudentProfile.this.arrayList, StudentProfile.this);
                                studentprofile_list.setAdapter(studentProfileAdapter);
                                studentProfileAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                if(isStudentAbsent){
                    myRef.child("absent").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1:snapshot.getChildren()){
                                StudentRegistrationModel model=snapshot1.getValue(StudentRegistrationModel.class);
                             arrayList.add(new StudentProfileModel(model.getFname(),"No","Yes",subject,model.getUniqueKey(),"No"));
                            }
                            if(arrayList.size()>0){
                                studentProfileAdapter = new StudentProfileAdapter(StudentProfile.this.arrayList, StudentProfile.this);
                                studentprofile_list.setAdapter(studentProfileAdapter);
                                studentProfileAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                if(isStudentLate){
                    myRef.child("late").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                StudentRegistrationModel model=snapshot1.getValue(StudentRegistrationModel.class);
                               arrayList.add(new StudentProfileModel(model.getFname(),"No","No",subject,model.getUniqueKey(),"Yes"));
                            }
                            if(arrayList.size()>0){
                                studentProfileAdapter = new StudentProfileAdapter(StudentProfile.this.arrayList, StudentProfile.this);
                                studentprofile_list.setAdapter(studentProfileAdapter);
                                studentProfileAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                if(progressDialog.isShowing()){
                    progressDialog.cancel();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

/*

        FirebaseDatabase studentdatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRefstudent = studentdatabase.getReference("UserDetails").child("Student");
        myRefstudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                studentUserIDList.clear();
                for (DataSnapshot postsnapshot : snapshot.getChildren()) {
                    RegistrationModel model = postsnapshot.getValue(RegistrationModel.class);
                    String firstname=model.getFirstName();
                    String lastname=model.getLastName();
                    String sidd=model.getSid();
                    String pass=model.getPass();
                    String coursess=model.getCourse();
                    String type=model.getType();
                    String key=postsnapshot.getKey();
                    StudentProfileModelss studentProfileModel=new StudentProfileModelss(firstname,lastname,sidd,pass,course,type,key);
                    studentUserIDList.add(studentProfileModel);
                    arrayList.add(new StudentProfileModel(firstname + lastname, "No", "yes",course,key));

                }


                for (int i=0;i<studentUserIDList.size();i++){
                    for(int j=0;j<presentList.size();j++){
                        if(studentUserIDList.get(i).getKey().equals(presentList.get(j))){
                            arrayList.set(i,new StudentProfileModel(studentUserIDList.get(i).getFirstName() + studentUserIDList.get(i).getLastName(), "yes", "no",course,studentUserIDList.get(i).getKey()));
                        }
                    }
                }

                *//*
                for (int i=0;i<studentUserIDList.size();i++){
                    for(int j=0;j<presentList.size();j++){
                        if(studentUserIDList.get(i).getKey().equals(presentList.get(j))){
                            arrayList.add(new StudentProfileModel(studentUserIDList.get(i).getFirstName() + studentUserIDList.get(i).getLastName(), "yes", "no"));
                        }
                        else{
                            arrayList.add(new StudentProfileModel(studentUserIDList.get(i).getFirstName() + studentUserIDList.get(i).getLastName(), "No", "yes"));
                        }
                    }
                }*//*







                studentProfileAdapter = new StudentProfileAdapter(arrayList, StudentProfile.this);
                studentprofile_list.setAdapter(studentProfileAdapter);
                studentProfileAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.getMessage();
            }
        });*/
    }
}