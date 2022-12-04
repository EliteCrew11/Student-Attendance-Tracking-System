package com.example.sampleapplication.professor.ui.InstructorAttendence;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleapplication.CommonUtils;
import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.StudentModel;
import com.example.sampleapplication.professor.StudentKeyModel;
import com.example.sampleapplication.student.FacultyCountModel;
import com.example.sampleapplication.student.models.StudentPieModel;
import com.example.sampleapplication.student.ui.gallery.StudentPeopleAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InstructorAttendenceFragemnt extends Fragment {

    RecyclerView instructor_listview;
    ProgressDialog progressDialog;
    InstructorAttendenceAdapter instructorAttendenceAdapter;
    TextView id_nodata;
    ConstraintLayout id_data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructor_attendence_fragemnt, container, false);
        instructor_listview = view.findViewById(R.id.instructor_listview);
        id_data = view.findViewById(R.id.id_data);
        id_nodata = view.findViewById(R.id.id_nodata);
        instructor_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(instructor_listview.getContext(),
                new LinearLayoutManager(getActivity()).getOrientation());
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.line_decoration));

        instructor_listview.addItemDecoration(dividerItemDecoration);

        ArrayList<FacultyCountModel> registrationModelArrayList = new ArrayList<>();
        if (CommonUtils.isConnectedToInternet(getActivity())) {
            try {
                getStudentDate(registrationModelArrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "No Internet Connection....", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String subject = sharedpreferences.getString("CourseType", "");
        String facultyIS = sharedpreferences.getString("TEACHERID", "");

        instructorAttendenceAdapter = new InstructorAttendenceAdapter(registrationModelArrayList, getActivity(), facultyIS,subject);
        ;
        instructor_listview.setAdapter(instructorAttendenceAdapter);
        return view;
    }

    private void getStudentDate(ArrayList<FacultyCountModel> registrationModelArrayList) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String subject = sharedpreferences.getString("CourseType", "");
        String teacherUniqueID = sharedpreferences.getString("TEACHERID", "");


        DatabaseReference myRef = database.getReference("MarkAttendence").child(teacherUniqueID).child(subject);

        //  String studentID = FirebaseAuth.getInstance().getUid();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                Log.d("Student Course", snapshot.toString());
                registrationModelArrayList.clear();

                for (DataSnapshot sddd : snapshot.getChildren()) {

                    String totalPresent=sddd.child("StudentCount").child("present").getValue(String.class);
                    String totalAbsent=sddd.child("StudentCount").child("absent").getValue(String.class);
                    String totalLate=sddd.child("StudentCount").child("late").getValue(String.class);
                    String date = sddd.getKey();

                    if (date.equals("0000")) {

                    } else {
                        FacultyCountModel studentPieModels = new FacultyCountModel(totalPresent, totalAbsent,totalLate, date);
                        registrationModelArrayList.add(studentPieModels);
                    }
                }
                if (registrationModelArrayList.isEmpty()) {
                    id_nodata.setVisibility(View.VISIBLE);
                    id_data.setVisibility(View.GONE);
                } else {
                    id_nodata.setVisibility(View.GONE);
                    id_data.setVisibility(View.VISIBLE);
                    instructorAttendenceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_LONG).show();
            }
        });

    }
}