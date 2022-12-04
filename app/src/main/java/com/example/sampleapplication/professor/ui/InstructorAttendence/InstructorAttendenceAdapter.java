package com.example.sampleapplication.professor.ui.InstructorAttendence;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.StudentModel;
import com.example.sampleapplication.professor.StudentProfile;
import com.example.sampleapplication.student.FacultyCountModel;

import java.util.ArrayList;


public class InstructorAttendenceAdapter extends RecyclerView.Adapter<InstructorAttendenceAdapter.RowViewHolder>{

    ArrayList<FacultyCountModel> studentList;
    FragmentActivity activity;
    String faculty;
    String coursee;


    public InstructorAttendenceAdapter(ArrayList<FacultyCountModel> datalist, FragmentActivity allStudentsAttendence, String facultyIS, String subject) {
        this.studentList=datalist;
        this.activity=allStudentsAttendence;
        this.faculty=facultyIS;
        this.coursee=subject;
    }


    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.instructor_list_item, parent, false);
        RowViewHolder viewHolder = new RowViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
            FacultyCountModel modal = studentList.get(position);
            holder.txt_date.setText(modal.getDate());
            holder.txt_present.setText(modal.getNoOfPresent());
            holder.txt_absent.setText(modal.getNoOfAbsent());
            holder.txt_late.setText(modal.getNoofLate());

        holder.student_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, StudentProfile.class);
                intent.putExtra("COURSE",studentList.get(position).getNoofLate());
                intent.putExtra("DATE",studentList.get(position).getDate());
                intent.putExtra("FACULTY",faculty);
                intent.putExtra("subject",coursee);
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date,txt_present,txt_absent,txt_late;
        ConstraintLayout student_layout;
        public RowViewHolder(View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_present = itemView.findViewById(R.id.txt_present);
            txt_absent = itemView.findViewById(R.id.txt_absent);
            txt_late = itemView.findViewById(R.id.txt_late);
            student_layout = itemView.findViewById(R.id.student_layout);
        }
    }
}