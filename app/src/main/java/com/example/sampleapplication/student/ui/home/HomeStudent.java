package com.example.sampleapplication.student.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sampleapplication.InstructorEnrolledActivity;
import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.StudentEnrolledCoursesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class HomeStudent extends Fragment {

    TextView id_instructorName;
    TextView id_instructorEmail;
    TextView id_instructorTimes,description_text;
    TextView id_officehours;
    TextView id_hall;
    TextView id_tel,hrss_text;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
     //   ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Hello");
        id_tel = view.findViewById(R.id.id_tel);
        id_officehours = view.findViewById(R.id.id_officehours);
        description_text = view.findViewById(R.id.description_text);
        hrss_text = view.findViewById(R.id.hrss_text);
        id_instructorEmail = view.findViewById(R.id.id_instructorEmail);
        id_instructorName = view.findViewById(R.id.id_instructorName);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    private void getData() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String subject = sharedpreferences.getString("CourseType", "");
        String course = checkCourse(subject);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("InstructorDetails").child("Details").child(course);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                Log.d("Student Course", snapshot.toString());
                InstrcutorDetailsModel instrcutorDetailsModel = snapshot.getValue(InstrcutorDetailsModel.class);
                if (instrcutorDetailsModel != null) {
                    id_instructorName.setText(instrcutorDetailsModel.getInstructor());
                    id_instructorEmail.setText(instrcutorDetailsModel.getEmail());
                    description_text.setText(instrcutorDetailsModel.getDescription());
                    hrss_text.setText("MF: " + instrcutorDetailsModel.getOfficeMWF() + " ; T:" + instrcutorDetailsModel.getOfficeTR() + " or by appointment");
                    id_tel.setText(instrcutorDetailsModel.getPhone());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(getActivity(), "Data Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String checkCourse(String subject) {
        if (subject.toLowerCase().contains("java")) {
            return "java";
        } else if (subject.toLowerCase().contains("gdp")) {
            return "gdp";
        } else if (subject.contains("Big Data (13800)")) {
            return "Big";
        } else if (subject.contains("Project Management (13800)")) {
            return "Project";
        } else {
            return "java";
        }

    }
}
