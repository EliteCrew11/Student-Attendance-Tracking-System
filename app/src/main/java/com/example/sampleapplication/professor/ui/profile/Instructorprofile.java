package com.example.sampleapplication.professor.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sampleapplication.R;

import java.util.ArrayList;


public class Instructorprofile extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setdata();
    }

    private void setdata() {
        instructorProfileModels = new ArrayList<>();
        instructorProfileModels.add(new InstructorProfileModel(
                "JAVA", "Dr. Ratan Lal", "Associate Professor", "rlal@nwmissouri.edu", "CH2205", "660-562-1588", R.drawable.lal));
        instructorProfileModels.add(new InstructorProfileModel(
                "GDP ||", "Dr. Aziz Fellah", "Associate Professor", "afellah@nwmissouri.edu", "CH2220", "660-562-0803", R.drawable.fellah));
        instructorProfileModels.add(new InstructorProfileModel(
                "Project Management", "Dr. Charles Hoot", "Associate Professor", "hoot@nwmissouri.edu", "CH2163", "660-562-1801", R.drawable.hoot));
        instructorProfileModels.add(new InstructorProfileModel(
                "BIG DATA", "Dr. Ajay Bandi", "Associate Professor / Program Coordinator", "ajay@nwmissouri.edu", "CH2250", "660-562-1199", R.drawable.bandi));

    }


    ArrayList<InstructorProfileModel> instructorProfileModels;
    ImageView instructor_pic;
    TextView facultyName;
    TextView facultytype, facMail, facphone, facoffice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructorprofile, container, false);
        instructor_pic = view.findViewById(R.id.instructor_pic);
        facultyName = view.findViewById(R.id.facultyName);
        facultytype = view.findViewById(R.id.facultytype);
        facMail = view.findViewById(R.id.facMail);
        facphone = view.findViewById(R.id.facphone);
        facoffice = view.findViewById(R.id.facoffice);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String subject = sharedpreferences.getString("CourseType", "");


        if (subject.toLowerCase().contains("java")) {
            InstructorProfileModel pos = instructorProfileModels.get(0);
            facultyName.setText(pos.getName());
            facultytype.setText(pos.getType());
            facphone.setText(pos.getPhone());
            facMail.setText(pos.getEmail());
            facoffice.setText(pos.getOffice());
            instructor_pic.setImageResource(pos.photo);
        } else if (subject.toLowerCase().contains("gdp")) {

            InstructorProfileModel pos = instructorProfileModels.get(1);
            facultyName.setText(pos.getName());
            facultytype.setText(pos.getType());
            facphone.setText(pos.getPhone());
            facMail.setText(pos.getEmail());
            facoffice.setText(pos.getOffice());
            instructor_pic.setImageResource(pos.photo);
        } else if (subject.toLowerCase().contains("project")) {

            InstructorProfileModel pos = instructorProfileModels.get(2);
            facultyName.setText(pos.getName());
            facultytype.setText(pos.getType());
            facphone.setText(pos.getPhone());
            facMail.setText(pos.getEmail());
            facoffice.setText(pos.getOffice());
            instructor_pic.setImageResource(pos.photo);
        } else if (subject.toLowerCase().contains("big")) {

            InstructorProfileModel pos = instructorProfileModels.get(3);
            facultyName.setText(pos.getName());
            facultytype.setText(pos.getType());
            facphone.setText(pos.getPhone());
            facMail.setText(pos.getEmail());
            facoffice.setText(pos.getOffice());
            instructor_pic.setImageResource(pos.photo);
        } else {

        }

    }
}