package com.example.sampleapplication.student.ui.gallery;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleapplication.AllStudentsAttendence;
import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.StudentModel;
import com.example.sampleapplication.student.models.StudentRegistrationModel;

import java.util.ArrayList;

public class StudentPeopleAdapter extends RecyclerView.Adapter<StudentPeopleAdapter.RowViewHolder>{

    ArrayList<StudentRegistrationModel> studentList;
    FragmentActivity activity;


    public StudentPeopleAdapter(ArrayList<StudentRegistrationModel> datalist, FragmentActivity allStudentsAttendence) {
        this.studentList=datalist;
        this.activity=allStudentsAttendence;
    }


    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.student_people_list_item, parent, false);
      RowViewHolder viewHolder = new RowViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {


            holder.txtnames.setText(studentList.get(position).getFname() + " " + studentList.get(position).getLname());
            holder.txtsid.setText(studentList.get(position).getStudentMailID());


    }

    void setHeaderBg(TextView view){
    //    view.setBackgroundResource(R.drawable.table_header_cell_bg);
        view.setTypeface(view.getTypeface(), Typeface.BOLD_ITALIC);

    }

    void setContentBg(View view){
      //  view.setBackgroundResource(R.drawable.table_content_cell_bg);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void setFilterList(ArrayList<StudentRegistrationModel> list) {
         this.studentList=list;
         notifyDataSetChanged();
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtnames,txtsid;
        public RowViewHolder(View itemView) {
            super(itemView);
            txtnames = itemView.findViewById(R.id.txtnames);
            txtsid = itemView.findViewById(R.id.txtsid);

        }
    }
}