package com.example.sampleapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class TableViewAdapter extends RecyclerView.Adapter<TableViewAdapter.RowViewHolder>{

    ArrayList<StudentModel> studentList;


    public TableViewAdapter(ArrayList<StudentModel> datalist, AllStudentsAttendence allStudentsAttendence) {
        this.studentList=datalist;
    }


    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.table_list_item, parent, false);
        RowViewHolder viewHolder = new RowViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        int rowPos = holder.getAdapterPosition();
        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            setHeaderBg(holder.txtRank);
            setHeaderBg(holder.txtMovieName);
            setHeaderBg(holder.txtYear);
            setHeaderBg(holder.txtCost);
            holder.txtCost.setText("Budget (in Millions)");
            holder.txtYear.setText("Year");
            holder.txtMovieName.setText("Name");
            holder.txtRank.setText("Rank");

        } else {
            StudentModel modal = studentList.get(rowPos - 1);

            setContentBg(holder.txtRank);
            setContentBg(holder.txtMovieName);
            setContentBg(holder.txtYear);
            setContentBg(holder.txtCost);
            holder.txtCost.setText(modal.excluded);
            holder.txtYear.setText(modal.present);
            holder.txtMovieName.setText(modal.getName());
            holder.txtRank.setText(modal.getAbsent());

        }

    }

    void setHeaderBg(View view){
        view.setBackgroundResource(R.drawable.table_header_cell_bg);
    }

    void setContentBg(View view){
        view.setBackgroundResource(R.drawable.table_content_cell_bg);
    }

    @Override
    public int getItemCount() {
        return studentList.size() + 1;
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtRank,txtMovieName,txtYear,txtCost;
        public RowViewHolder(View itemView) {
            super(itemView);
            txtRank = itemView.findViewById(R.id.txtRank);
            txtMovieName = itemView.findViewById(R.id.txtMovieName);
            txtYear= itemView.findViewById(R.id.txtYear);
            txtCost = itemView.findViewById(R.id.txtCost);
        }
    }
}