package com.example.sampleapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllStudentsAttendence extends AppCompatActivity {

    RecyclerView recyclerViewMovieList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students_attendence);
        recyclerViewMovieList = findViewById(R.id.recyclerViewMovieList);
        recyclerViewMovieList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovieList.setAdapter(new TableViewAdapter(getData(), AllStudentsAttendence.this));

    }

    ArrayList<StudentModel> getData() {
        ArrayList<StudentModel> arrayList = new ArrayList<>();
        arrayList.add(new StudentModel("ram", "22", "2", "2"));
        arrayList.add(new StudentModel("ram", "22", "2", "2"));
        arrayList.add(new StudentModel("ram", "22", "2", "2"));
        arrayList.add(new StudentModel("ram", "22", "2", "2"));
        arrayList.add(new StudentModel("ram", "22", "2", "2"));
        arrayList.add(new StudentModel("ram", "22", "2", "2"));
        arrayList.add(new StudentModel("ram", "22", "2", "2"));
        arrayList.add(new StudentModel("ram", "22", "2", "2"));
        return arrayList;
    }

}
