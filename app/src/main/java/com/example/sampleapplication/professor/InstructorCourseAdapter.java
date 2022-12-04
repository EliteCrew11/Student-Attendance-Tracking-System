package com.example.sampleapplication.professor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleapplication.InstructorEnrolledActivity;
import com.example.sampleapplication.InstructorOptionsActivity;
import com.example.sampleapplication.R;
import com.example.sampleapplication.StudentEnrolledCoursesActivity;
import com.example.sampleapplication.StudentOptionsActivity;

import java.util.ArrayList;


public class InstructorCourseAdapter extends RecyclerView.Adapter<InstructorCourseAdapter.ViewHolder>{
    private ArrayList<String> listdata;
    InstructorEnrolledActivity context;

    public InstructorCourseAdapter(InstructorEnrolledActivity studentEnrolledCoursesActivity, ArrayList<String> course) {
        this.listdata=course;
        this.context=studentEnrolledCoursesActivity;
    }


    @NonNull
    @Override
    public InstructorCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        InstructorCourseAdapter.ViewHolder viewHolder = new InstructorCourseAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorCourseAdapter.ViewHolder holder, int position) {
        if(listdata.get(position).contains("Java")){
            holder.textView.setText("Java (13204)");
        } else  if(listdata.get(position).contains("Project")){
            holder.textView.setText("Project Management (23123)");
        }
        else  if(listdata.get(position).contains("GDP")){
            holder.textView.setText("GDP (13896)");
        } else  if(listdata.get(position).contains("Big")){
            holder.textView.setText("Big Data (13900)");
        } else {
            holder.textView.setText(listdata.get(position).toLowerCase());
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences =context.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("CourseType", listdata.get(position).toString());
                editor.commit();
                Intent intent=new Intent(context, IntructorHomePage.class);
                intent.putExtra("CourseType",listdata.get(position).toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.text_course);
        }
    }
}

