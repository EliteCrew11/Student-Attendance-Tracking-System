package com.example.sampleapplication.professor.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sampleapplication.R;
import com.example.sampleapplication.databinding.FragmentHomeBinding;
import com.example.sampleapplication.student.ui.home.InstrcutorDetailsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    TextView id_instructorName;
    TextView id_instructorEmail;
    TextView id_instructorTimes,description_text,hrss_text;
    TextView id_officehours;
    TextView id_hall;
    TextView id_tel;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_instructor, container, false);
        id_tel = view.findViewById(R.id.id_tel);
        id_officehours = view.findViewById(R.id.id_officehours);
        hrss_text = view.findViewById(R.id.hrss_text);
        description_text = view.findViewById(R.id.description_text);
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
        String emailiddd = sharedpreferences.getString("EMAILID", "");
        String course = checkCourse(subject);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("InstructorDetails").child("Details");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                Log.d("Student Course", snapshot.toString());
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    InstrcutorDetailsModel instrcutorDetailsModel = snapshot1.getValue(InstrcutorDetailsModel.class);
                    if (instrcutorDetailsModel != null) {
                        String email=instrcutorDetailsModel.getEmail().toString().toLowerCase();
                        if(email.equalsIgnoreCase(emailiddd.toLowerCase())){
                            id_instructorEmail.setText(instrcutorDetailsModel.getEmail());
                            id_instructorName.setText(instrcutorDetailsModel.getInstructor());
                            id_tel.setText(instrcutorDetailsModel.getPhone());
                        }

                    }
                }

                if(snapshot.hasChildren()){
                    myRef.child(course).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            InstrcutorDetailsModel instrcutorDetailsModel = snapshot.getValue(InstrcutorDetailsModel.class);
                            if (instrcutorDetailsModel != null) {
                              description_text.setText(instrcutorDetailsModel.getDescription());
                                hrss_text.setText("MF: " + instrcutorDetailsModel.getOfficeMWF() + " ; T:" + instrcutorDetailsModel.getOfficeTR() + " or by appointment");
                              }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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