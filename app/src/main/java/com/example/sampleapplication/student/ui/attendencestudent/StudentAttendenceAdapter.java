package com.example.sampleapplication.student.ui.attendencestudent;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleapplication.R;
import com.example.sampleapplication.RegistrationModel;
import com.example.sampleapplication.StudentModel;

import java.util.ArrayList;


public class StudentAttendenceAdapter extends RecyclerView.Adapter<StudentAttendenceAdapter.RowViewHolder>{

    ArrayList<StudentModel> studentList;
    FragmentActivity activity;


    public StudentAttendenceAdapter(ArrayList<StudentModel> datalist, FragmentActivity allStudentsAttendence) {
        this.studentList=datalist;
        this.activity=allStudentsAttendence;
    }


    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.student_atttendence_list_item, parent, false);
        RowViewHolder viewHolder = new RowViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        int rowPos = holder.getAdapterPosition();
        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            setHeaderBg(holder.txtdate);

            setHeaderBg(holder.txtpresent);
            setHeaderBg(holder.txtabsent);

            holder.txtdate.setText("Date");
            holder.txtpresent.setText("Present");
            holder.txtabsent.setText("Absent");

        } else {
            StudentModel modal = studentList.get(rowPos - 1);

            setContentBg(holder.txtdate);
            setContentBg(holder.txtpresent);
            setContentBg(holder.txtabsent);

            holder.txtdate.setText(modal.getDate());
            holder.txtpresent.setText(modal.getPresent());
            holder.txtabsent.setText(modal.getAbsent());

        }

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
        return studentList.size() + 1;
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtdate,txtpresent,txtabsent;
        public RowViewHolder(View itemView) {
            super(itemView);
            txtdate = itemView.findViewById(R.id.txtdate);
            txtpresent = itemView.findViewById(R.id.txtpresent);
            txtabsent = itemView.findViewById(R.id.txtabsent);

        }
    }
}