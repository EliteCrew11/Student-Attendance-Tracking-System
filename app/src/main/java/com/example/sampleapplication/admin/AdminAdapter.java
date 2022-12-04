package com.example.sampleapplication.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sampleapplication.R;
import com.example.sampleapplication.student.StudentHomePage;
import java.util.ArrayList;




public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>{
    private ArrayList<AdminRegistrationmodel> listdata;
    AdminDashboard context;

    public AdminAdapter(AdminDashboard studentEnrolledCoursesActivity, ArrayList<AdminRegistrationmodel> course) {
        this.listdata=course;
        this.context=studentEnrolledCoursesActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.admin_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.admin_name.setText(listdata.get(position).getFirstName()+" "+listdata.get(position).getLastName());
        holder.admin_sid.setText(listdata.get(position).getSid());
        holder.admin_role.setText(listdata.get(position).getType());

        holder.admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, StudentProfileUpdate.class);
                intent.putExtra("FIRSTNAME",listdata.get(position).getFirstName());
                intent.putExtra("LASTNAME",listdata.get(position).getLastName());
                intent.putExtra("SID",listdata.get(position).getSid());
                intent.putExtra("PASS",listdata.get(position).getPass());
                intent.putExtra("COURSE",listdata.get(position).getCourse());
                intent.putExtra("TYPE",listdata.get(position).getType());
                intent.putExtra("KEY",listdata.get(position).getKey());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public void setFilterList(ArrayList<AdminRegistrationmodel> list) {
        this.listdata=list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView admin_name,admin_sid,admin_role;
        Button admin_btn;
;
        public ViewHolder(View itemView) {
            super(itemView);
            this.admin_name = (TextView) itemView.findViewById(R.id.admin_name);
            this.admin_role = (TextView) itemView.findViewById(R.id.admin_role);
            this.admin_btn = (Button) itemView.findViewById(R.id.admin_btn);
            this.admin_sid = (TextView) itemView.findViewById(R.id.admin_sid);
        }
    }
}