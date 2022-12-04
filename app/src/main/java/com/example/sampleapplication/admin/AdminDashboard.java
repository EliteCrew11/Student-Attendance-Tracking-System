package com.example.sampleapplication.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleapplication.CommonUtils;
import com.example.sampleapplication.InstructorEnrolledActivity;
import com.example.sampleapplication.MainActivity;
import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.StudentEnrolledCoursesActivity;
import com.example.sampleapplication.StudentLogin;
import com.example.sampleapplication.student.StudentCourseAdapter;
import com.example.sampleapplication.student.models.StudentRegistrationModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminDashboard extends AppCompatActivity {

    RecyclerView recyclerview;
    AdminAdapter adapter;
    ImageView ic_logout, id_back;
    ProgressDialog progressDialog;
    ArrayList<AdminRegistrationmodel> arrayList;
    SearchView id_searchview;

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonUtils.isConnectedToInternet(AdminDashboard.this)) {
            try {
                //    getuserdetails(arrayList);
                getstudnentdetails(arrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(AdminDashboard.this, "No Internet Connection....", Toast.LENGTH_SHORT).show();
        }/*
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        ic_logout = findViewById(R.id.id_logout);
        recyclerview = findViewById(R.id.recyclerview);
        id_back = findViewById(R.id.id_back);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> course = new ArrayList<>();
        id_searchview=findViewById(R.id.id_searchview);
        // id_searchview.setQueryHint(Html.fromHtml("<font color = #000000>" + getResources().getString(R.string.search_hint) + "</font>"));
        int id = id_searchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) id_searchview.findViewById(id);
        textView.setTextColor(Color.BLACK);
        id_searchview.clearFocus();
        arrayList = new ArrayList<>();


        adapter = new AdminAdapter(AdminDashboard.this, arrayList);
        recyclerview.setAdapter(adapter);

        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

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

        ic_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, AdminLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void filterList(String newText) {
        ArrayList<AdminRegistrationmodel> list = new ArrayList<>();
        for (AdminRegistrationmodel model : arrayList) {
            if (model.FirstName.toLowerCase().contains(newText.toLowerCase())) {
                list.add(model);
            }

        }


        if(list.isEmpty()){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilterList(list);
        }
    }

    private void getstudnentdetails(ArrayList<AdminRegistrationmodel> arrayList) {

        progressDialog = new ProgressDialog(AdminDashboard.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Student");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    StudentRegistrationModel registrationModel = snapshots.getValue(StudentRegistrationModel.class);
                    String firstname = registrationModel.getFname();
                    String lasttname = registrationModel.getLname();
                    String sid = registrationModel.getStudentMailID();
                    String pass = registrationModel.getPassword();
                    String course = registrationModel.getCourseList();
                    String type = registrationModel.getType();
                    String eky = snapshots.getKey();
                    arrayList.add(new AdminRegistrationmodel(firstname, lasttname, sid, pass, course, type, eky));
                }
                progressDialog.cancel();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressDialog.cancel();
    }

    private void getuserdetails(ArrayList<AdminRegistrationmodel> arrayList) {

        progressDialog = new ProgressDialog(AdminDashboard.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserDetails").child("Instructor");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshots : snapshot.getChildren()) {
                    RegistrationModel registrationModel = snapshots.getValue(RegistrationModel.class);
                    String firstname = registrationModel.getFirstName();
                    String lasttname = registrationModel.getLastName();
                    String sid = registrationModel.getSid();
                    String pass = registrationModel.getPass();
                    String course = registrationModel.getCourse();
                    String type = registrationModel.getType();
                    String eky = snapshots.getKey();
                    arrayList.add(new AdminRegistrationmodel(firstname, lasttname, sid, pass, course, type, eky));
                }
                progressDialog.cancel();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressDialog.cancel();

    }

    @Override
    public void onBackPressed() {

    }
}