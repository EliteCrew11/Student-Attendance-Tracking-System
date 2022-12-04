package com.example.sampleapplication.professor;

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
import com.example.sampleapplication.student.FacultyCountModel;
import com.example.sampleapplication.student.models.StudentProfileModel;

import java.util.ArrayList;




public class StudentProfileAdapter extends RecyclerView.Adapter<StudentProfileAdapter.RowViewHolder>{

    ArrayList<StudentProfileModel> studentList;
    FragmentActivity activity;
    String faculty;


    public StudentProfileAdapter(ArrayList<StudentProfileModel> datalist, FragmentActivity allStudentsAttendence) {
        this.studentList=datalist;
        this.activity=allStudentsAttendence;
    }


    public void setFilterList(ArrayList<StudentProfileModel> list) {
        this.studentList=list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.instructor_studentlist_item, parent, false);
        RowViewHolder viewHolder = new RowViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
            StudentProfileModel modal = studentList.get(position);
            holder.txt_date.setText(modal.getName());
            holder.txt_present.setText(modal.getPresent());
            holder.txt_absent.setText(modal.getAbsent());
            holder.txt_late.setText(modal.getLate());

            holder.student_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(activity,InstructorStudentProfile.class);
                    intent.putExtra("STUDENTKEY",modal.getStudentKey());
                    intent.putExtra("COURSE",modal.getCourse());
                    intent.putExtra("NAME",modal.getName());
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
            txt_late = itemView.findViewById(R.id.txt_late);
            txt_present = itemView.findViewById(R.id.txt_present);
            txt_absent = itemView.findViewById(R.id.txt_absent);
            student_layout = itemView.findViewById(R.id.student_layout);
        }
    }
}